package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.exception.ConferencException.ChairChangeConferenceStageFailException;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChairIdentityService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private PaperRepository paperRepository;
    private ReviewRepository reviewRepository;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public ChairIdentityService(UserRepository userRepository, InvitationRepository invitationRepository,
                        ConferenceRepository conferenceRepository, PaperRepository paperRepository,
                                ReviewRepository reviewRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        this.paperRepository = paperRepository;
        this.reviewRepository = reviewRepository;
        this.invitationRepository = invitationRepository;
    }

    /**
     * changeConferenceStatus(chair 改变会议阶段)
     * PREPARATION      -->
     * CONTRIBUTION     -->(there is a strategy for review)
     * REVIEWING        -->(all PC reviewed and checked the result)
     * REVIEWED         -->(all rebuttal papers rechecked)
     * ENDING
     *
     * @param request the UserRequest request
     * @return return conference's id and changed stage
     */
    public String changeConferenceStage(ChairChangeConferenceStageRequest request) {
        // TODO: Better implementation should be found
        if(request.getChangedStage() == Conference.Stage.REVIEWING){
            throw new ChairChangeConferenceStageFailException("You should not start reviewing by this request");
            //return "{\"message\":\" You should not start reviewing by this request\"}";
        }

        User chair = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if(chair == null || conference == null || !conference.getChairman().getId().equals(chair.getId())){
            // invalid check, not chair
            throw new ChairChangeConferenceStageFailException("Invalid identity");
        }
        // chair can only change stage step by step with limits, only admin can trace back or skip steps
        if(UtilityService.isConferenceChangeStageValid(conference, request.getChangedStage(), paperRepository)){
            conference.setStage(request.getChangedStage());
            conferenceRepository.save(conference);
        }
        return "{\"message\":\"" + conference.getConferenceFullName() + "'s Stage is " + conference.getStage().toString() + "\"}";
    }

    /**
     *
     * @param request request from user
     * @return message of dealing result
     */
    public String startReviewing(ChairStartReviewingRequest request){
        User chair = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        ChairStartReviewingRequest.Strategy strategy = request.getStrategy();
        //System.out.println("START REVIEWING : " + request);
        if(chair == null || conference == null || strategy == null || !conference.getChairman().getId().equals(chair.getId())){
            throw new ChairChangeConferenceStageFailException("Invalid request");
        }

        if(conference.getReviewerSet().size() < 3 || paperRepository.findPapersByConference(conference).size() == 0){
            throw new ChairChangeConferenceStageFailException("Too less PC Members or No paper to review!");
            //return "{\"message\":\"Too less PC Members or No paper to review!\"}";
        }

        if(strategy == ChairStartReviewingRequest.Strategy.TOPIC_RELATED){
            if(paperAssignment_TOPIC_RELATED(conference)){
                conferenceStartReviewing(conference);
                return "{\"message\":\" Reviewing start!\"}";
            }else{
                throw new ChairChangeConferenceStageFailException("No valid assignment!");
                //return "{\"message\":\" No valid assignment!\"}";
            }
        }else if(strategy == ChairStartReviewingRequest.Strategy.RANDOM){
            if(paperAssignment_RANDOM(conference)){
                conferenceStartReviewing(conference);
                return "{\"message\":\" Reviewing start!\"}";
            }else{
                throw new ChairChangeConferenceStageFailException("No valid assignment!");
                //return "{\"message\":\" No valid assignment!\"}";
            }
        }
        throw new ChairChangeConferenceStageFailException("Unknown assignment strategy!");
        //return "{\"message\":\" Unknown assignment strategy!\"}";
    }

    private void conferenceStartReviewing(Conference conference){
        Set<Paper> papers = paperRepository.findPapersByConference(conference);
        for (Paper paper: papers
        ) {
            paper.setStatus(Paper.Status.REVIEWING);
        }
        paperRepository.saveAll(papers);
        conference.setStage(Conference.Stage.REVIEWING);
        conferenceRepository.save(conference);
    }

    private Set<User> getValidReviewersOfPaperByTopic(Paper paper){
        Set<Review> reviews = reviewRepository.findReviewsByConference(paper.getConference());
        Set<User> allValidReviewers = new HashSet<>();
        // get all topics of a paper
        Set<String> topicSet = new HashSet<>(Arrays.asList(paper.getTopics()));
        for (Review review: reviews) {
            for (String reviewerTopic: review.getTopics()) {
                if(topicSet.contains(reviewerTopic)){
                    allValidReviewers.add(review.getReviewer());
                }
            }
        }
        //check whether benefit conflict
        String[][] paperAuthors=paper.getPaperAuthors();
        for(User user : allValidReviewers){
            for (String[] author : paperAuthors){
             if (author[0].equals(user.getFullName())&&author[3].equals(user.getEmail()))
                 allValidReviewers.remove(user);
            }
        }

        return allValidReviewers;
    }

    private boolean paperAssignment_TOPIC_RELATED(Conference conference) {
        //Set<Review> reviews = reviewRepository.findReviewsByConference(conference);
        Set<Paper> papers = paperRepository.findPapersByConference(conference);
        for (Paper paper : papers) {
            Set<User> allValidReviewers = getValidReviewersOfPaperByTopic(paper);
            if (allValidReviewers.size() < 3) {
                allValidReviewers = conference.getReviewerSet();
            }
            Set<User> selectedReviewers = UtilityService.selectObjectsFromBaseSet(allValidReviewers, 3);
            if (selectedReviewers == null) {
                return false;
            }
            for (User reviewer : selectedReviewers) {
                paper.getReviewers().add(reviewer);
            }
            paperRepository.save(paper);
            for (User reviewer : selectedReviewers) {
                Review review = reviewRepository.findReviewByConferenceAndReviewer(conference, reviewer);
                review.getPapers().add(paperRepository.findByPaperId(paper.getPaperId()));
                reviewRepository.save(review);
            }
        }
        return true;
    }


    private boolean paperAssignment_RANDOM(Conference conference) {
        List<User> reviewersCopy = new ArrayList<>(conference.getReviewerSet());
        List<Paper> papersCopy1 = new ArrayList<>(paperRepository.findPapersByConference(conference));
        List<Paper> papersCopy = new ArrayList<>(papersCopy1);
        for (Paper paper: papersCopy1) {
            papersCopy.add(paper);
            papersCopy.add(paper);
        }

        int average = papersCopy.size() / reviewersCopy.size();
        Random random = UtilityService.random;
        for (User reviewer : reviewersCopy) {
            Review review = reviewRepository.findReviewByConferenceAndReviewer(conference, reviewer);
            Set<Long> assignedPaperIds = new HashSet<>();
            for (int i = 0; i < average; i++) {
                Paper randomPaper = papersCopy.get(random.nextInt(papersCopy.size()));
                while(assignedPaperIds.contains(randomPaper.getPaperId())){
                    randomPaper = papersCopy.get(random.nextInt(papersCopy.size()));
                }
                Paper newRandomPaper = paperRepository.findByPaperId(randomPaper.getPaperId());
                newRandomPaper.getReviewers().add(reviewer);
                //System.out.println("IN RANDOM: " + reviewer);
                assignedPaperIds.add(randomPaper.getPaperId());
                paperRepository.save(newRandomPaper);
                review.getPapers().add(paperRepository.findByPaperId(randomPaper.getPaperId()));
                papersCopy.remove(randomPaper);
            }
            reviewRepository.save(review);
        }

        return assignPapersToReviewer(papersCopy, reviewersCopy, conference);
    }

    private boolean assignPapersToReviewer(List<Paper> leftPapers, List<User> reviewersCopy, Conference conference){
        Random random = UtilityService.random;
        for (Paper paper : leftPapers) {
            User randomReviewer = reviewersCopy.get(random.nextInt(reviewersCopy.size()));
            Review review = reviewRepository.findReviewByConferenceAndReviewer(conference, randomReviewer);
            Set<Long> assignedPaperIds = new HashSet<>();
            for(Paper assignedPaper : review.getPapers()){
                assignedPaperIds.add(assignedPaper.getPaperId());
            }
            while(assignedPaperIds.contains(paper.getPaperId())){
                randomReviewer = reviewersCopy.get(random.nextInt(reviewersCopy.size()));
                review = reviewRepository.findReviewByConferenceAndReviewer(conference, randomReviewer);
                assignedPaperIds = new HashSet<>();
                for(Paper assignedPaper : review.getPapers()){
                    assignedPaperIds.add(assignedPaper.getPaperId());
                }
            }
            // add paper's reviewer
            Paper newPaper = paperRepository.findByPaperId(paper.getPaperId());
            newPaper.getReviewers().add(randomReviewer);
            paperRepository.save(newPaper);
            // add reviewer's paper
            review.getPapers().add(paperRepository.findByPaperId(paper.getPaperId()));

            reviewersCopy.remove(randomReviewer);
            reviewRepository.save(review);
        }
        return true;
    }

    /**
     * chair search Reviewers to invite
     *
     * @param request the ChairRequest request
     * @return return successful message
     */
    public List<JSONObject> searchReviewers(ChairSearchReviewersRequest request) {
        User chair = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if(!conference.getChairman().getId().equals(chair.getId())){
            // invalid search, not chair
            return null;
        }
        List<JSONObject> list = new ArrayList<>();
        Set<User> usersAll = userRepository.findAll();
        Set<Long> reviewerIdSet = new HashSet<>();
        for (User reviewer: conference.getReviewerSet()
        ) {
            reviewerIdSet.add(reviewer.getId());
        }
        Set<User> users = new HashSet<>();
        String targetFullname = request.getTargetFullName();
        for (User user:usersAll
        ) {
            if(user.getFullName().contains(targetFullname)){
                if(!user.getUsername().equals("admin") && !user.getId().equals(chair.getId()) && !reviewerIdSet.contains(user.getId())){
                    users.add(user);
                }
            }
        }
        for (User user:users
        ) {
            list.add(user.toStandardJson());
        }
        return list;
    }

    /**
     * chair send invitations
     *
     * @param request the ChairRequest request
     * @return return successful message
     */
    public String sendInvitation(ChairSendInvitationRequest request) {
        User chair = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        int validNum = 0;
        if(!conference.getChairman().getId().equals(chair.getId())){
            // invalid send, not chair
            return "{\"message\":\" You are not the chair! " + validNum + " invitation has been send!\"}";
        }

        String[] targetNames = request.getReviewerUsername();
        String message = request.getMessage();

        for (String targetName: targetNames) {
            User reviewer = this.userRepository.findByUsername(targetName);
            // chair and PC member cannot be invited
            if(!reviewer.getId().equals(chair.getId()) && !conference.getReviewerSet().contains(reviewer)){
                // invitation has sent before
                Set<Invitation> invitations = invitationRepository.findByReviewerAndConferenceAndStatus(reviewer, conference, Invitation.Status.PENDING);
                if(invitations.size() == 1){ continue; }

                validNum++;
                Invitation newInvitation = new Invitation(conference, chair, reviewer, message);
                this.invitationRepository.save(newInvitation);
            }
        }
        // return how many valid invitations has been send
        return "{\"message\":\"" + validNum + " invitation has been send!\"}";
    }

    /**
     * check send invitations(用户查看自己发出的邀请函)
     *
     * @param request the ChairCheckInvitationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> checkInvitations(ChairCheckInvitationsRequest request) {
        User chair = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if(!conference.getChairman().getId().equals(chair.getId())){
            // invalid check, not chair
            return null;
        }

        Set<Invitation> invitationSet = invitationRepository.findByConference(conference);
        List<JSONObject> list = Lists.newArrayList();
        if(request.getStatus() == null){
            for (Invitation eachInvitation : invitationSet) {
                list.add(eachInvitation.toStandardJson());
            }
        }else{
            Invitation.Status statusLimit = request.getStatus();
            for (Invitation eachInvitation : invitationSet) {
                if (eachInvitation.getStatus() == statusLimit)
                    list.add(eachInvitation.toStandardJson());
            }
        }
        return list;
    }
}
