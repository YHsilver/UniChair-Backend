package fudan.se.lab2.controller.messagePage;

import fudan.se.lab2.controller.messagePage.request.UserCheckMyInvitationsRequest;
import fudan.se.lab2.controller.messagePage.request.UserDecideInvitationsRequest;
import fudan.se.lab2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePageController {
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(MessagePageController.class);

    @Autowired
    public MessagePageController(UserService userService) {
        this.userService = userService;
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
        System.out.println(request);
        return ResponseEntity.ok(userService.decideInvitations(request));
    }
}
