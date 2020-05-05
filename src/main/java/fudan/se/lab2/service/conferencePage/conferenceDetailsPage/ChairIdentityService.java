package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ChairIdentityService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public ChairIdentityService(UserRepository userRepository, InvitationRepository invitationRepository,
                        ConferenceRepository conferenceRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
    }


    /**
     * changeConferenceStatus(chair 改变会议阶段)
     *
     * @param request the UserRequest request
     * @return return conference's id and changed stage
     */
    public String changeConferenceStage(ChairChangeConferenceStageRequest request) {
        // TODO: Better implementation should be found
        if(request.getChangedStage() == Conference.Stage.REVIEWING){
            return "{\"message\":\" You should not start reviewing by this request\"}";
        }

        User chair = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if(!conference.getChairman().getId().equals(chair.getId())){
            // invalid check, not chair
            return null;
        }
        // chair can only change stage step by step with limits, only admin can trace back or skip steps
        if(UtilityService.isConferenceChangeStageValid(conference, request.getChangedStage())){
            conference.setStage(request.getChangedStage());
            conferenceRepository.save(conference);
            chair.addConference(conference);
        }
        return conference.getConferenceFullName() + "'s Stage is " + conference.getStage().toString();
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
        if(chair == null || conference == null || strategy == null || conference.getChairman().equals(chair)){
            return "{\"message\":\" Invalid request!\"}";
        }

        if(conference.getReviewerSet().size() < 3 || conference.getPaperSet().size() == 0){
            return "{\"message\":\" Too less PC Members or No paper to review!\"}";
        }

        if(strategy == ChairStartReviewingRequest.Strategy.TOPIC_RELATED){
            if(UtilityService.paperAssignment_TOPIC_RELATED(conference)){
                return "{\"message\":\" Reviewing start!\"}";
            }else{
                return "{\"message\":\" No valid assignment!\"}";
            }
        }else if(strategy == ChairStartReviewingRequest.Strategy.RANDOM){
            if(UtilityService.paperAssignment_RANDOM(conference)){
                return "{\"message\":\" Reviewing start!\"}";
            }else{
                return "{\"message\":\" No valid assignment!\"}";
            }
        }


        return "{\"message\":\" Unknown assignment strategy!\"}";
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
        Set<User> users = new HashSet<>();
        String targetFullname = request.getTargetFullName();
        for (User user:usersAll
             ) {
            if(user.getFullName().contains(targetFullname)){
                users.add(user);
            }
        }
        // avoid inviting chair himself
        users.remove(chair);
        // avoid inviting reviewers have been invited and accepted
        users.removeAll(conference.getReviewerSet());
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
                Set<Invitation> invitations = invitationRepository.findByReviewerAndConferenceIdAndStatus(reviewer, conference.getConferenceId(), Invitation.Status.PENDING);
                if(invitations.size() == 1){ continue; }

                validNum++;
                Invitation newInvitation = new Invitation(conference.getConferenceId(), conference.getConferenceFullName(), chair,
                        reviewer, message);
                this.invitationRepository.save(newInvitation);
                chair.getSendInvitations().add(newInvitation);
                reviewer.getMyInvitations().add(newInvitation);
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

        Set<Invitation> invitationSet = invitationRepository.findByConferenceId(conference.getConferenceId());
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
