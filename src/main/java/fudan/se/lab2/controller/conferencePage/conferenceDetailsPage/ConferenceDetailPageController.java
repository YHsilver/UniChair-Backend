package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorModifyPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.*;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetConferenceDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetIdentityRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetPaperPdfFileRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerSubmitPaperReviewedRequest;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.AuthorIdentityService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.ChairIdentityService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.GenericConferenceService;
import fudan.se.lab2.service.conferencePage.conferenceDetailsPage.ReviewerIdentityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;

@RestController
public class ConferenceDetailPageController {

    private ChairIdentityService chairIdentityService;
    private AuthorIdentityService authorIdentityService;
    private ReviewerIdentityService reviewerIdentityService;
    private GenericConferenceService genericConferenceService;

    // 日志
    private Logger logger = LoggerFactory.getLogger(ConferenceDetailPageController.class);

    @Autowired
    public ConferenceDetailPageController(ChairIdentityService chairIdentityService,
                                          AuthorIdentityService authorIdentityService,
                                          ReviewerIdentityService reviewerIdentityService,
                                          GenericConferenceService genericConferenceService) {
        this.chairIdentityService = chairIdentityService;
        this.authorIdentityService = authorIdentityService;
        this.reviewerIdentityService = reviewerIdentityService;
        this.genericConferenceService = genericConferenceService;
    }
    /* AUTHOR IDENTITY */

    // author get all details of a paper
    @PostMapping("/system/authorGetMyPaperDetails")
    public ResponseEntity<?> handleUserRequest(@RequestBody AuthorGetMyPaperDetailsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(authorIdentityService.getMyPaperDetails(request));
    }

    // author get all papers or all papers in a certain conference
    @PostMapping("/system/authorGetMyPapers")
    public ResponseEntity<?> handleUserRequest(@RequestBody AuthorGetMyPapersRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(authorIdentityService.getMyPapers(request));
    }

    // author modify a submitted paper
    @PostMapping("/system/authorModifyPaper")
    public ResponseEntity<?> handleAuthorModifyPaperRequest(@RequestParam("file") MultipartFile file,
                                               @RequestParam("paperId") Long paperId,
                                               @RequestParam("topics") String[] topics,
                                               @RequestParam("title") String title,
                                               @RequestParam("authors") String[] authors,
                                               @RequestParam("summary") String summary,
                                               @RequestParam("token") String token,
                                               HttpServletRequest modifyRequest) {

        AuthorModifyPaperRequest authorModifyPaperRequest = new AuthorModifyPaperRequest(token, paperId,
                topics, title, authors, summary, file);
        return ResponseEntity.ok(authorIdentityService.modifyPaper(authorModifyPaperRequest));
    }

    /* CHAIR IDENTITY */
    // chair获取用户信息以邀请 PC member
    @PostMapping("/system/chairSearchReviews")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairSearchReviewersRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairIdentityService.searchReviewers(request));
    }



    // chair发出邀请请别人成为 PC member
    @PostMapping("/system/chairSendInvitation")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairSendInvitationRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairIdentityService.sendInvitation(request));
    }

    // chair查看自己发出的邀请函
    @PostMapping("/system/chairCheckInvitations")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairCheckInvitationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairIdentityService.checkInvitations(request));
    }

    // chair改变会议阶段
    @PostMapping("/system/chairChangeConferenceStage")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairChangeConferenceStageRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairIdentityService.changeConferenceStage(request));
    }

    // chair开启审稿
    @PostMapping("/system/chairStartReviewing")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairStartReviewingRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairIdentityService.startReviewing(request));
    }

    /* REVIEWER IDENTITY */

    @PostMapping("/system/reviewerGetPapers")
    public ResponseEntity<?> handleUserRequest(@RequestBody ReviewerGetPapersRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(reviewerIdentityService.getPapers(request));
    }

    @PostMapping("/system/reviewerGetPaperDetails")
    public ResponseEntity<?> handleUserRequest(@RequestBody ReviewerGetPaperDetailsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(reviewerIdentityService.getPaperDetails(request));
    }

    @PostMapping("/system/submitPaperReviewed")
    public ResponseEntity<?> handleUserRequest(@RequestBody ReviewerSubmitPaperReviewedRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(reviewerIdentityService.submitPaperReviewed(request));
    }

    /* GENERAL IDENTITY */

    // user投稿
    @PostMapping("/system/userSubmitPaper")
    public ResponseEntity<?> handleUserRequest(@RequestParam("file") MultipartFile file,
                                               @RequestParam("conferenceId") Long conferenceId,
                                               @RequestParam("topics") String[] topics,
                                               @RequestParam("title") String title,
                                               @RequestParam("authors") String[] authors,
                                               @RequestParam("summary") String summary,
                                               @RequestParam("token") String token,
                                               HttpServletRequest submitRequest) {
        UserSubmitPaperRequest request = new UserSubmitPaperRequest(token, conferenceId, topics, title,
                authors, summary, file);
        return ResponseEntity.ok(genericConferenceService.submitPaper(request));
    }

    // user获取会议详细信息
    @PostMapping("/system/userGetConferenceDetails")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetConferenceDetailsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(genericConferenceService.getConferenceDetails(request));
    }

    // user获取自己在当前会议的身份
    @PostMapping("/system/userGetIdentity")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetIdentityRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(genericConferenceService.getIdentity(request));
    }

    // user获取一个PAPER 的 PDF file
    @PostMapping("/system/userGetPaperPdfFile")
    public ResponseEntity<byte[]> handleUserRequest(@RequestBody UserGetPaperPdfFileRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return genericConferenceService.getPaperPdfFile(request);
    }

}
