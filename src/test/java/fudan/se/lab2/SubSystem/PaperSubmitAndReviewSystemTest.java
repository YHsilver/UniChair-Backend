package fudan.se.lab2.SubSystem;

import fudan.se.lab2.Tester;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorRebuttalResultRequset;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerCheckPaperReviewedRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerSubmitPaperReviewedRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.exception.ConferencException.ReviewerReviewPaperFailException;
import fudan.se.lab2.generator.StringGenerator;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.AuthorIdentityService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.ReviewerIdentityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PaperSubmitAndReviewSystemTest {
    /*
     * 对稿件的整个生命周期流程进行测试，包括
     * 1、投稿
     * 2、Author修改稿件
     * 3、PC审稿
     * 4、PC修改或不修改评分
     * 5、Author提出或不提出Rebuttal
     * 6、PC针对Rebuttal修改或不修改评分
     * 7、发布最终评审结果
     * */

    private Tester tester;

    @Autowired
    public PaperSubmitAndReviewSystemTest(Tester tester){
        this.tester = tester;
    }

    @Test
    void systemTest(){
        // 得到与子系统相关的 Service 实例，包括 authorIdentityService, reviewerIdentityService
        AuthorIdentityService authorIdentityService = tester.authorIdentityService;
        ReviewerIdentityService reviewerIdentityService = tester.reviewerIdentityService;

        // 初始化投稿环境
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User[] reviewers = tester.addReviewers(conference, 2);
        tester.startContribution(conference);
        conference = tester.conferenceRepository.findByConferenceId(conference.getConferenceId());
        // author 投稿
        User author = tester.getNewUser();
        String authorToken = tester.tokenUtil.generateToken(author);
        Paper paper = tester.submitNewPaper(conference, author);
        // author 查看自己的投稿
        assertEquals(1, authorIdentityService.getMyPapers(new AuthorGetMyPapersRequest(authorToken, conference.getConferenceId())).size());
        assertEquals(paper.toStandardJson(), authorIdentityService.getMyPaperDetails(new AuthorGetMyPaperDetailsRequest(authorToken, paper.getPaperId())));
        // author 修改自己的投稿
        Paper newPaper = tester.modifyPaper(paper);
        assertEquals(paper.getPaperId(), newPaper.getPaperId());
        assertNotEquals(paper.toStandardJson(), authorIdentityService.getMyPaperDetails(new AuthorGetMyPaperDetailsRequest(authorToken, newPaper.getPaperId())));
        assertEquals(newPaper.toStandardJson(), authorIdentityService.getMyPaperDetails(new AuthorGetMyPaperDetailsRequest(authorToken, newPaper.getPaperId())));
        // pc 审稿
        tester.startReviewing(conference);
        conference = tester.conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);
        // pc 修改或确认自己的评分
        reviewerIdentityService.modifyPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tester.tokenUtil.generateToken(reviewers[0]), newPaper.getPaperId(),
                1, "NEW COMMENTS", "HIGH"));
        Exception exception = new Exception("init");
        try{
            // 再次修改
            reviewerIdentityService.modifyPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tester.tokenUtil.generateToken(reviewers[0]), newPaper.getPaperId(),
                    -2, "NEW COMMENTS", "HIGH"));
        }catch (ReviewerReviewPaperFailException e){
            exception = e;
        }
        assertTrue(exception instanceof  ReviewerReviewPaperFailException);
        assertEquals("You have checked/modified your review for this paper!", exception.getMessage());
        reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(tester.tokenUtil.generateToken(reviewers[1]), newPaper.getPaperId()));
        reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(tester.tokenUtil.generateToken(chair), newPaper.getPaperId()));
        tester.startReviewed(conference);
        conference = tester.conferenceRepository.findByConferenceId(conference.getConferenceId());
        // author 提交 rebuttal 信息
        String rebuttal = "rebuttal Message" + StringGenerator.getRandomString();
        authorIdentityService.sendRebuttal(new AuthorRebuttalResultRequset(tester.tokenUtil.generateToken(author),
                rebuttal, paper.getPaperId()));
        assertEquals(rebuttal, tester.paperRepository.findByPaperId(paper.getPaperId()).getRebuttal());
        // pc 对 rebuttal 再次修改或决定不修改
        reviewerIdentityService.modifyPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tester.tokenUtil.generateToken(reviewers[0]), newPaper.getPaperId(),
                -2, "NEW COMMENTS", "HIGH"));
        Exception exception2 = new Exception("init");
        try{
            // 再次修改
            reviewerIdentityService.modifyPaperReviewed(new ReviewerSubmitPaperReviewedRequest(tester.tokenUtil.generateToken(reviewers[0]), newPaper.getPaperId(),
                    -2, "NEW COMMENTS", "HIGH"));
        }catch (ReviewerReviewPaperFailException e){
            exception2 = e;
        }
        assertTrue(exception2 instanceof  ReviewerReviewPaperFailException);
        assertEquals("You have checked/modified your review for this paper!", exception2.getMessage());
        reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(tester.tokenUtil.generateToken(reviewers[1]), newPaper.getPaperId()));
        reviewerIdentityService.checkPaperReviewed(new ReviewerCheckPaperReviewedRequest(tester.tokenUtil.generateToken(chair), newPaper.getPaperId()));
        newPaper = tester.paperRepository.findByPaperId(newPaper.getPaperId());
        assertEquals(-2, newPaper.getGrades()[0]);
        assertEquals(-2, newPaper.getGrades()[1]);
        assertEquals(-2, newPaper.getGrades()[2]);
    }

}
