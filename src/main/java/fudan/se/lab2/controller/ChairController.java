package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.chair.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.request.chair.ChairCheckSendInvitationsRequest;
import fudan.se.lab2.controller.request.chair.ChairInviteReviewersRequest;
import fudan.se.lab2.controller.request.chair.ChairSearchReviewersRequest;
import fudan.se.lab2.service.ChairService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hyf
 * 这个类控制“chair请求”
 * 具体响应内容的类是 ChairService
 */

@RestController
public class ChairController {

    private ChairService chairService;

    // 日志
    Logger logger = LoggerFactory.getLogger(ChairController.class);

    @Autowired
    public ChairController(ChairService chairService) {
        this.chairService = chairService;
    }

    // 获取用户
    @PostMapping("/system/getUsers")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairSearchReviewersRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairService.getReviewers(request));
    }

    // 邀请别人成为 PC members
    @PostMapping("/system/inviteReviewers")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairInviteReviewersRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairService.inviteReviewers(request));
    }

    // 查看自己发出的邀请函
    @PostMapping("/system/checkSendInvitations")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairCheckSendInvitationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(chairService.checkSendInvitations(request));
    }

    // 改变会议阶段
    @PostMapping("/system/changeConferenceStage")
    public ResponseEntity<?> handleUserRequest(@RequestBody ChairChangeConferenceStageRequest request) {
        logger.debug(request.toString());
//        System.out.println(request.toString());
        return ResponseEntity.ok(chairService.changeConferenceStage(request));
    }

}



