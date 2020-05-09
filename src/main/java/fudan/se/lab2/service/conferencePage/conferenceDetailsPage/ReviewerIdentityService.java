package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerSubmitPaperReviewedRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.exception.ConferencException.ReviewerReviewPaperFailException;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.ReviewRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewerIdentityService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private ReviewRepository reviewRepository;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public ReviewerIdentityService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                    PaperRepository paperRepository, ReviewRepository reviewRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.reviewRepository = reviewRepository;
        this.tokenUtil = tokenUtil;
    }

    public List<JSONObject> getPapers(ReviewerGetPapersRequest request){
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if(!UtilityService.isValidReviewer(conference, reviewer) || !conference.isAfterOrEqualsStage(Conference.Stage.REVIEWING)){
            return null;
        }
        //Set<Long> paperIdSet = conference.getReviewerAndPapersMap().get(reviewer);
        Set<Paper> paperSet = reviewRepository.findReviewByConferenceAndReviewer(conference, reviewer).getPapers();
        List<JSONObject> paperList = new ArrayList<>();
        for (Paper paper: paperSet
        ) {
            paperList.add(paper.toBriefJson(reviewer.getId()));
        }
        return paperList;
    }

    public JSONObject getPaperDetails(ReviewerGetPaperDetailsRequest request){
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        if(!UtilityService.isValidReviewer(paper, reviewer)){
            return null;
        }
        return paper.toStandardJson(reviewer.getId());
    }

    public String submitPaperReviewed(ReviewerSubmitPaperReviewedRequest request){
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Paper paper = paperRepository.findByPaperId(request.getPaperId());

        if(!UtilityService.isValidReviewer(paper, reviewer)){
            throw new ReviewerReviewPaperFailException("You are not the reviewer of this paper!");
        }

        int i = 0;
        for(; i < Paper.REVIEWER_NUM; i++){
            if(paper.getReviewers().get(i).getId().equals(reviewer.getId())){
                break;
            }
        }
        if(paper.getIsReviewed()[i]!=null){
            throw new ReviewerReviewPaperFailException("You have reviewed this paper!");
        }
        if(!UtilityService.checkStringLength(request.getComment(), 1)){
            throw new ReviewerReviewPaperFailException("Comments format error!");
        }
        if(request.getGrade() > 2 || request.getGrade() < -2 || request.getGrade() == 0){
            throw new ReviewerReviewPaperFailException("Invalid grade!");
        }
        if(request.getConfidence() == null){
            throw new ReviewerReviewPaperFailException("Confidence error!!");
        }
        paper.getComments()[i] = request.getComment();
        paper.getGrades()[i] = request.getGrade();
        paper.getConfidences()[i] = request.getConfidence();
        paper.getIsReviewed()[i] = true;
        paperRepository.save(paper);
        return "{\"message\":\"Review paper success!\"}";
    }

}
