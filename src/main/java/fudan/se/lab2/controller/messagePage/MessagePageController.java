package fudan.se.lab2.controller.messagePage;

import fudan.se.lab2.controller.messagePage.request.UserCheckMyInvitationsRequest;
import fudan.se.lab2.controller.messagePage.request.UserDecideInvitationsRequest;
import fudan.se.lab2.service.messagePage.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePageController {
    private MessageService messageService;

    private Logger logger = LoggerFactory.getLogger(MessagePageController.class);

    @Autowired
    public MessagePageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // 查看自己收到的邀请函
    @PostMapping("/system/userCheckMyInvitations")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserCheckMyInvitationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(messageService.userCheckMyInvitations(request));
    }

    // 是够接受邀请
    @PostMapping("/system/userDecideMyInvitations")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserDecideInvitationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request);
        return ResponseEntity.ok(messageService.userDecideInvitations(request));
    }
}
