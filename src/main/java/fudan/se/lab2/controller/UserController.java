package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.user.*;
import fudan.se.lab2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hyf
 * 这个类控制“用户请求”
 * 具体响应内容的类是 UserService
 */

@RestController
public class UserController {

    private UserService userService;

    // 日志
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 来自 localhost:80/setUpConference 的请求
    // 申请会议
    @PostMapping("/setUpConference")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserSetUpConferenceRequest request) {
        logger.debug("setUpConferenceForm: " + request.toString());
//        System.out.println(request);
        return ResponseEntity.ok(userService.setUpConference(request));
    }

    // 获取自己申请的会议
    @PostMapping("/system/getMyConference")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetMyConferenceRequest request) {
        logger.debug(request.toString());
//        System.out.println(request.toString());
        return ResponseEntity.ok(userService.getMyConference(request));
    }

    // 获取所有现在通过的会议，以投稿
    @PostMapping("/system/getConference")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetAllConferenceRequest request) {
        logger.debug(request.toString());
//        System.out.println(request.toString());
        return ResponseEntity.ok(userService.getAllConference(request));
    }

    // 获取会议详细信息
    @PostMapping("/system/getConferenceDetails")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetConferenceDetailsRequest request) {
        logger.debug(request.toString());
//        System.out.println(request.toString());
        return ResponseEntity.ok(userService.getConferenceDetails(request));
    }

    // 获取会议详细信息
    @PostMapping("/system/getConferenceDetails")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetIdentityRequest request) {
        logger.debug(request.toString());
//        System.out.println(request.toString());
        return ResponseEntity.ok(userService.getIdentity(request));
    }

    // 投稿
    @PostMapping("/system/submitPaper")
    public ResponseEntity<?> handleUserRequest(@RequestParam("file") MultipartFile file,
                                               @RequestParam("conferenceId") Long conferenceId,
                                               @RequestParam("title") String title,
                                               @RequestParam("file") String summary,
                                               @RequestParam("author") String author,
                                               @RequestParam("file") String token) {
        UserSubmitPaperRequest request = new UserSubmitPaperRequest(author, conferenceId, title,
                summary, file, token);
        logger.debug(request.toString());
        System.out.println(request.toString());
        try {
            return ResponseEntity.ok(userService.submitPaper(request));
        } catch (IOException e) {
            logger.trace("context", e);   // Compliant
            return ResponseEntity.ok("something wrong with your paper qwq");
        }
    }

    // 查看自己收到的邀请函
    @PostMapping("/system/checkMyInvitations")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserCheckMyInvitationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(userService.checkMyInvitations(request));
    }

    // 是够接受邀请
    @PostMapping("/system/decideMyInvitations")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserDecideInvitationsRequest request) {
        logger.debug(request.toString());
//        System.out.println(request);
        return ResponseEntity.ok(userService.decideInvitations(request));
    }
}



