package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.PaperPosts;
import fudan.se.lab2.exception.ConferencException.ReviewerNotFoundException;
import fudan.se.lab2.exception.ConferencException.ReviewerReviewPaperFailException;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
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
    private PaperPostsRepository paperPostsRepository;

    @Autowired
    public ReviewerIdentityService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                   PaperRepository paperRepository, ReviewRepository reviewRepository,
                                   JwtTokenUtil tokenUtil, PaperPostsRepository paperPostsRepository) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.reviewRepository = reviewRepository;
        this.tokenUtil = tokenUtil;
        this.paperPostsRepository = paperPostsRepository;
    }

    public List<JSONObject> getPapers(ReviewerGetPapersRequest request) {
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference conference = conferenceRepository.findByConferenceId(request.getConferenceId());
        if (!UtilityService.isValidReviewer(conference, reviewer) || !conference.isAfterOrEqualsStage(Conference.Stage.REVIEWING)) {
            return null;
        }
        //Set<Long> paperIdSet = conference.getReviewerAndPapersMap().get(reviewer);
        Set<Paper> paperSet = reviewRepository.findReviewByConferenceAndReviewer(conference, reviewer).getPapers();
        List<JSONObject> paperList = new ArrayList<>();
        for (Paper paper : paperSet
        ) {
            paperList.add(paper.toBriefJson(reviewer.getId()));
        }
        return paperList;
    }

    public JSONObject getPaperDetails(ReviewerGetPaperDetailsRequest request) {
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        if (!UtilityService.isValidReviewer(paper, reviewer)) {
            return null;
        }
        return paper.toStandardJson(reviewer.getId());
    }

    public String submitPaperReviewed(ReviewerSubmitPaperReviewedRequest request) {
        checkSubmitReviewRequestValid(request);
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));

        int i = findReviewerIndex(reviewer, paper);

        if (paper.getIsReviewed()[i] != null) {
            throw new ReviewerReviewPaperFailException("You have reviewed this paper!");
        }

        writeValidReview(request, i);
        paper = paperRepository.findByPaperId(request.getPaperId());
        paper.getIsReviewed()[i] = true;
        if (paper.isAllReviewed()) {
            paper.setStatus(Paper.Status.REVIEWED);
        }
        paperRepository.save(paper);
        return "{\"message\":\"Review paper success!\"}";
    }

    public String modifyPaperReviewed(ReviewerSubmitPaperReviewedRequest request) {
        checkSubmitReviewRequestValid(request);
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));

        int i = findReviewerIndex(reviewer, paper);

        if ((paper.getIsReviewChecked()[i] != null && paper.getStatus() == Paper.Status.REVIEWED) ||
                (paper.getIsRebuttalChecked()[i] != null && paper.getStatus() == Paper.Status.CHECKED)
        ) {
            throw new ReviewerReviewPaperFailException("You have checked/modified your review for this paper!");
        }

        writeValidReview(request, i);
        paper = paperRepository.findByPaperId(request.getPaperId());
        paper.getIsReviewChecked()[i] = true;

        if (paper.getStatus() == Paper.Status.CHECKED) {
            paper.getIsRebuttalChecked()[i] = true;
        }

        if (paper.isAllChecked()) {
            paper.setStatus(Paper.Status.CHECKED);
        }
        paperRepository.save(paper);
        return "{\"message\":\"Modify reviewed paper success!\"}";
    }

    private void checkSubmitReviewRequestValid(ReviewerSubmitPaperReviewedRequest request) {

        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        //System.out.println(reviewer);
        Paper paper = paperRepository.findByPaperId(request.getPaperId());

        if (!UtilityService.isValidReviewer(paper, reviewer)) {
            throw new ReviewerReviewPaperFailException("You are not the reviewer of this paper!");
        }

        if (paper.getStatus() != Paper.Status.REVIEWING && paper.getStatus() != Paper.Status.REVIEWED &&
                paper.getStatus() != Paper.Status.CHECKED
        ) {
            throw new ReviewerReviewPaperFailException("Paper status not valid!");
        }

        if (!UtilityService.checkStringLength(request.getComment(), 1)) {
            throw new ReviewerReviewPaperFailException("Comments format error!");
        }
        if (request.getGrade() > 2 || request.getGrade() < -2 || request.getGrade() == 0) {
            throw new ReviewerReviewPaperFailException("Invalid grade!");
        }
        if (request.getConfidence() == null) {
            throw new ReviewerReviewPaperFailException("Confidence error!!");
        }
    }

    private int findReviewerIndex(User reviewer, Paper paper) {
        int i = 0;
        for (; i < Paper.REVIEWER_NUM; i++) {
            if (paper.getReviewers().get(i).getId().equals(reviewer.getId())) {
                break;
            }
        }
        return i;
    }

    private void writeValidReview(ReviewerSubmitPaperReviewedRequest request, int i) {
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        paper.getComments()[i] = request.getComment();
        paper.getGrades()[i] = request.getGrade();
        paper.getConfidences()[i] = request.getConfidence();
        paperRepository.save(paper);
    }


    public String checkPaperReviewed(ReviewerCheckPaperReviewedRequest request) {
        User reviewer = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Paper paper = paperRepository.findByPaperId(request.getPaperId());

        if (!UtilityService.isValidReviewer(paper, reviewer)) {
            throw new ReviewerNotFoundException("You are not the reviewer of this paper !");
        }

        int i = findReviewerIndex(reviewer,paper);

        if ((paper.getIsReviewChecked()[i] != null && paper.getStatus() == Paper.Status.REVIEWED) ||
                (paper.getIsRebuttalChecked()[i] != null && paper.getStatus() == Paper.Status.CHECKED)
        ) {
            throw new ReviewerReviewPaperFailException("You have checked/modified your review for this paper!");
        }


        paper.getIsReviewChecked()[i] = true;

        if (paper.getStatus() == Paper.Status.CHECKED) {
            paper.getIsRebuttalChecked()[i] = true;
        }

        if (paper.isAllChecked()) {
            paper.setStatus(Paper.Status.CHECKED);
        }
        paperRepository.save(paper);
        return "{\"message\":\"Check reviewed paper success!\"}";
    }

    public JSONObject getPaperRebuttal(ReviewerGetRebuttalRequest request) {
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        User user = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        if (!UtilityService.isValidReviewerOrChair(paper, user) && !paper.getAuthor().getId().equals(user.getId())) {
            throw new ReviewerNotFoundException("Your identity is not valid to get the information!");
        }

        try {
            return UtilityService.String2Json("{\"rebuttal\":\"" + paper.getRebuttal() + "\"}");
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<JSONObject> getPaperComment(ReviewerGetCommentJudgeRequest request) {
        return getPost(request, 1);

    }

    public List<JSONObject> getPaperJudgment(ReviewerGetCommentJudgeRequest request) {
        return getPost(request, 2);
    }

    private List<JSONObject> getPost(ReviewerGetCommentJudgeRequest request, int i) {
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        User user = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        if (!UtilityService.isValidReviewerOrChair(paper, user)) {
            throw new ReviewerNotFoundException("You are not the reviewer or chair of this paper !");
        }
        if (i == 1) return paper.getJSONPost1();
        else if (i == 2) return paper.getJSONPost2();
        else throw new RuntimeException("wrong method call: arguments false! ");
    }

    public String sendComment(ReviewerSendCommentJudgeRequest request) {
        setPost(request, 1);
        return "{\"message\":\"Send comment success!\"}";
    }

    public String sendJudgment(ReviewerSendCommentJudgeRequest request) {
        setPost(request, 2);
        return "{\"message\":\"Send judgment success!\"}";
    }

    private void setPost(ReviewerSendCommentJudgeRequest request, int i) {
        Paper paper = paperRepository.findByPaperId(request.getPaperId());
        User user = userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        if (!UtilityService.isValidReviewerOrChair(paper, user)) {
            throw new ReviewerNotFoundException("You are not the reviewer or chair of this paper !");
        }
        PaperPosts post = new PaperPosts(user, request.getMessage());
        paperPostsRepository.save(post);

        if (i == 1) paper.addOneToPost1(paperPostsRepository.findByPostsId(post.getPostsId()));
        else if (i == 2) paper.addOneToPost2(paperPostsRepository.findByPostsId(post.getPostsId()));
        else throw new RuntimeException("wrong method call: arguments false! ");


        paperRepository.save(paper);

    }
}
