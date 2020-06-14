package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.Tester;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorRebuttalResultRequset;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairStartReviewingRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.PaperPosts;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.StringGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewerIdentityServiceTest {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private ReviewRepository reviewRepository;
    private JwtTokenUtil tokenUtil;
    private ReviewerIdentityService reviewerIdentityService;
    private GenericConferenceService genericConferenceService;
    private ChairIdentityService chairIdentityService;
    private PaperPostsRepository paperPostsRepository;
    private Tester tester;

    @Autowired
    public ReviewerIdentityServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                       PaperRepository paperRepository, ReviewRepository reviewRepository, JwtTokenUtil tokenUtil,
                                       InvitationRepository invitationRepository, PaperPostsRepository paperPostsRepository, Tester tester) {
        this.tester = tester;
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.paperPostsRepository=paperPostsRepository;
        this.conferenceRepository = conferenceRepository;
        this.reviewRepository = reviewRepository;
        this.tokenUtil = tokenUtil;
        this.genericConferenceService = new GenericConferenceService(userRepository, conferenceRepository, paperRepository,tokenUtil);
        this.reviewerIdentityService=new ReviewerIdentityService(userRepository,conferenceRepository,paperRepository,
                reviewRepository,tokenUtil,paperPostsRepository);
        this.chairIdentityService=new ChairIdentityService(userRepository,invitationRepository,conferenceRepository,paperRepository,reviewRepository,tokenUtil);
    }

    @Test
    void getPapers(){
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());

        List<JSONObject> expectedList = new ArrayList<>();
        expectedList.add(paperRepository.findByPaperId(paper.getPaperId()).toBriefJson(chair.getId()));
        assertEquals(expectedList, reviewerIdentityService.getPapers(
                new ReviewerGetPapersRequest(tokenUtil.generateToken(chair), conference.getConferenceId())));
    }

    @Test
    void getPaperDetails() {
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        JSONObject paperDetails = reviewerIdentityService.getPaperDetails(new ReviewerGetPaperDetailsRequest(tokenUtil.generateToken(reviewers[0]),
                paper.getPaperId()));
        assertEquals(paperRepository.findByPaperId(paper.getPaperId()).toStandardJson(reviewers[0].getId()) ,paperDetails);
        assertNull(reviewerIdentityService.getPaperDetails(new ReviewerGetPaperDetailsRequest(tokenUtil.generateToken(author),
                paper.getPaperId())));
    }

    @Test
    void submitPaperReviewed() {
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        for (User reviewer: reviewers) {
            //System.out.println(reviewer);
            String reviewerToken = tokenUtil.generateToken(reviewer);
            reviewerIdentityService.submitPaperReviewed(new ReviewerSubmitPaperReviewedRequest(reviewerToken,
                    paper.getPaperId(), 1, StringGenerator.getRandomString(), "LOW"));
        }
        reviewerIdentityService.submitPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tokenUtil.generateToken(chair),
                paper.getPaperId(), 1, StringGenerator.getRandomString(), "LOW"));
        paper = paperRepository.findByPaperId(paper.getPaperId());
        assertTrue(paper.isAllReviewed());
        assertEquals(1, paper.getGrades()[0]);
        assertEquals(1, paper.getGrades()[1]);
        assertEquals(1, paper.getGrades()[2]);
        assertEquals("LOW", paper.getConfidences()[0]);
        assertEquals("LOW", paper.getConfidences()[1]);
        assertEquals("LOW", paper.getConfidences()[2]);

    }

    @Test
    void modifyPaperReviewed() {
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);
        tester.reviewerCheckAllPaperReviewed(conference);
        tester.startReviewed(conference);

        String rebuttal = "rebuttal Message" + StringGenerator.getRandomString();
        tester.authorIdentityService.sendRebuttal(new AuthorRebuttalResultRequset(tokenUtil.generateToken(author),
                rebuttal, paper.getPaperId()));

        for (User reviewer: reviewers) {
            reviewerIdentityService.modifyPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tokenUtil.generateToken(reviewer), paper.getPaperId(),
                    2, "HIGH COMMENT", "VERY_HIGH"));
        }
        reviewerIdentityService.modifyPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tokenUtil.generateToken(chair), paper.getPaperId(),
                2, "HIGH COMMENT", "VERY_HIGH"));
        paper = paperRepository.findByPaperId(paper.getPaperId());
        assertTrue(paper.getIsRebuttalChecked()[0]);
        assertTrue(paper.getIsRebuttalChecked()[1]);
        assertTrue(paper.getIsRebuttalChecked()[2]);
        assertEquals(2, paper.getGrades()[0]);
        assertEquals(2, paper.getGrades()[1]);
        assertEquals(2, paper.getGrades()[2]);
    }

    @Test
    void checkPaperReviewed(){
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);
        for (User reviewer: reviewers) {
            reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(tokenUtil.generateToken(reviewer), paper.getPaperId()));
        }
        reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(tokenUtil.generateToken(chair), paper.getPaperId()));
        paper = paperRepository.findByPaperId(paper.getPaperId());
        assertTrue(paper.getIsReviewChecked()[0]);
        assertTrue(paper.getIsReviewChecked()[1]);
        assertTrue(paper.getIsReviewChecked()[2]);
    }

    @Test
    void getPaperRebuttal() {
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);
        tester.reviewerCheckAllPaperReviewed(conference);
        //move to reviewed

        tester.startReviewed(conference);
        //conference = conferenceRepository.findByConferenceId(conference.getConferenceId());c
        String rebuttal = "rebuttal Message" + StringGenerator.getRandomString();
        tester.authorIdentityService.sendRebuttal(new AuthorRebuttalResultRequset(tokenUtil.generateToken(author),
                rebuttal, paper.getPaperId()));

        JSONObject expected = null, actual;
        try{
            expected = UtilityService.String2Json("{\"rebuttal\":\"" + rebuttal + "\"}");
        }catch (ParseException e){
            e.printStackTrace();
        }
        actual = reviewerIdentityService.getPaperRebuttal(new ReviewerGetRebuttalRequest(tokenUtil.generateToken(reviewers[0]), paper.getPaperId()));
        assertEquals(expected, actual);
    }

    @Test
    void sendAndGetPaperComment() {
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);

        String message = "message: " + StringGenerator.getRandomString();
        reviewerIdentityService.sendComment(new ReviewerSendCommentJudgeRequest(tokenUtil.generateToken(chair), paper.getPaperId(), message));

        PaperPosts paperPosts = paperPostsRepository.findByUser(chair).iterator().next();
        assertNotNull(paperPosts);
        List<JSONObject> expectedList = new ArrayList<>();
        expectedList.add(paperPosts.tojSON());
        assertEquals(expectedList, reviewerIdentityService.getPaperComment(
                new ReviewerGetCommentJudgeRequest(tokenUtil.generateToken(reviewers[0]), paper.getPaperId())));
    }

    @Test
    void sendAndGetPaperJudgement() {
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 2);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);
        tester.reviewerCheckAllPaperReviewed(conference);
        //move to reviewed
        tester.startReviewed(conference);
        //conference = conferenceRepository.findByConferenceId(conference.getConferenceId());c
        String rebuttal = "rebuttal Message" + StringGenerator.getRandomString();
        tester.authorIdentityService.sendRebuttal(new AuthorRebuttalResultRequset(tokenUtil.generateToken(author),
                rebuttal, paper.getPaperId()));

        String message = "message: " + StringGenerator.getRandomString();
        reviewerIdentityService.sendJudgment(new ReviewerSendCommentJudgeRequest(tokenUtil.generateToken(chair), paper.getPaperId(), message));

        PaperPosts paperPosts = paperPostsRepository.findByUser(chair).iterator().next();
        assertNotNull(paperPosts);
        List<JSONObject> expectedList = new ArrayList<>();
        expectedList.add(paperPosts.tojSON());
        assertEquals(expectedList, reviewerIdentityService.getPaperJudgment(
                new ReviewerGetCommentJudgeRequest(tokenUtil.generateToken(reviewers[0]), paper.getPaperId())));
    }

}