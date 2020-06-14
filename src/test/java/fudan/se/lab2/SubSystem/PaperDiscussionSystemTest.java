package fudan.se.lab2.SubSystem;

import fudan.se.lab2.Tester;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorRebuttalResultRequset;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetCommentJudgeRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerSendCommentJudgeRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.PaperPosts;
import fudan.se.lab2.generator.StringGenerator;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.AuthorIdentityService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.ReviewerIdentityService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PaperDiscussionSystemTest {
    /*
     * Paper 的讨论功能是一个较为独立的子系统
     * */

    private Tester tester;

    @Autowired
    public PaperDiscussionSystemTest(Tester tester){
        this.tester = tester;
    }

    @Test
    void systemTest(){
        // 得到与子系统相关的 Service 实例，包括 authorIdentityService, reviewerIdentityService
        AuthorIdentityService authorIdentityService = tester.authorIdentityService;
        ReviewerIdentityService reviewerIdentityService = tester.reviewerIdentityService;

        // 初始化一个可 Discussion 的 Paper
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        tester.startContribution(conference);
        conference = tester.conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 3);
        assertTrue(tester.startReviewing(conference));
        conference = tester.conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);

        // pc 发言讨论
        String message = "message: " + StringGenerator.getRandomString();
        reviewerIdentityService.sendComment(new ReviewerSendCommentJudgeRequest(tester.tokenUtil.generateToken(chair), paper.getPaperId(), message));
        PaperPosts paperPosts = tester.paperPostsRepository.findByUser(chair).iterator().next();
        assertNotNull(paperPosts);
        List<JSONObject> expectedList = new ArrayList<>();
        expectedList.add(paperPosts.tojSON());
        // 检测讨论内容是否正确
        assertEquals(expectedList, reviewerIdentityService.getPaperComment(
                new ReviewerGetCommentJudgeRequest(tester.tokenUtil.generateToken(reviewers[0]), paper.getPaperId())));
        tester.reviewerCheckAllPaperReviewed(conference);
        // author 提交 rebuttal
        String rebuttal = "rebuttal Message" + StringGenerator.getRandomString();
        authorIdentityService.sendRebuttal(new AuthorRebuttalResultRequset(tester.tokenUtil.generateToken(author),
                rebuttal, paper.getPaperId()));

        // author 提交 rebuttal 后可以继续讨论
        message = "message: " + StringGenerator.getRandomString();
        reviewerIdentityService.sendJudgment(new ReviewerSendCommentJudgeRequest(tester.tokenUtil.generateToken(chair), paper.getPaperId(), message));
        message = "message: " + StringGenerator.getRandomString();
        reviewerIdentityService.sendJudgment(new ReviewerSendCommentJudgeRequest(tester.tokenUtil.generateToken(chair), paper.getPaperId(), message));
        assertEquals(2, reviewerIdentityService.getPaperJudgment(
                new ReviewerGetCommentJudgeRequest(tester.tokenUtil.generateToken(reviewers[0]), paper.getPaperId())).size());

    }


}
