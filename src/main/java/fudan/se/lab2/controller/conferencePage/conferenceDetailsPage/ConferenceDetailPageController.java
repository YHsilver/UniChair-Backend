package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairSendInvitationRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetConferenceDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetIdentityRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairCheckInvitationsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairSearchReviewersRequest;
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
import java.io.IOException;

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

    /* REVIEWER IDENTITY */

    /* GENERAL IDENTITY */

    // user投稿
    @PostMapping("/system/userSubmitPaper")
    public ResponseEntity<?> handleUserRequest(@RequestParam("file") MultipartFile file,
                                               @RequestParam("conferenceId") Long conferenceId,
                                               @RequestParam("title") String title,
                                               @RequestParam("summary") String summary,
                                               @RequestParam("token") String token,
                                               HttpServletRequest submitRequest) {
        UserSubmitPaperRequest request = new UserSubmitPaperRequest(token, conferenceId, title,
                summary, file);
        try {
            return ResponseEntity.ok(genericConferenceService.submitPaper(request));
        } catch (IOException e) {
            logger.trace("context", e);
            return ResponseEntity.ok("something wrong with your paper qwq");
        }
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

}
