package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.user.*;
import fudan.se.lab2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    // case SETUP:
    @PostMapping("/setUpConference")
    public ResponseEntity<?> setUpConference(@RequestBody UserSetUpConferenceRequest request) {
        logger.debug("setUpConferenceForm: " + request.toString());
//        System.out.println("setUpConferenceForm: " + request.toString());
        return ResponseEntity.ok(userService.setUpConference(request));
    }

    // 来自 localhost:80/system 的请求（用户）

    @PostMapping("/system/getConference")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetConferenceRequest request) {
        logger.debug("UserRequest: " + request.toString());
        System.out.println("UserRequest: " + request.toString());
        return ResponseEntity.ok(userService.getConference(request));
    }

    @PostMapping("/system/submitPaper")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserSubmitPaperRequest request) {
        logger.debug("UserRequest: " + request.toString());
        System.out.println("UserRequest: " + request.toString());
        return ResponseEntity.ok(userService.submitPaper(request));
    }

    @PostMapping("/system/inviteReviewers")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserInviteReviewersRequest request) {
        logger.debug("UserRequest: " + request.toString());
        System.out.println("UserRequest: " + request.toString());
        return ResponseEntity.ok(userService.inviteReviewers(request));
    }

    @PostMapping("/system/changeConferenceStatus")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairChangeConferenceStageRequest request) {
        logger.debug("UserRequest: " + request.toString());
        System.out.println("UserRequest: " + request.toString());
        return ResponseEntity.ok(userService.changeConferenceStatus(request));
    }

}



