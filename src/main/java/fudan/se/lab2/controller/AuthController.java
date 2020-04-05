package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.*;
import fudan.se.lab2.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LBW
 * 这个类控制响应的发送，因为有 @RestController
 * 具体响应内容的类是 AuthService
 */

@RestController
public class AuthController {

    private AuthService authService;

    // 日志
    // logging.file.path=/var/tmp/mylog.log
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 来自 localhost:80/register 的请求
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());
        System.out.println("RegistrationForm: " + request.toString());
        return ResponseEntity.ok(authService.register(request));
    }

    // 来自 localhost:80/login 的请求
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());
        System.out.println("LoginForm: " + request.toString());
        //ResponseEntity.ok().
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    // 来自 localhost:80/setUpConference 的请求
    @PostMapping("/setUpConference")
    public ResponseEntity<?> setUpConference(@RequestBody ConferenceRequest request) {
        logger.debug("setUpConferenceForm: " + request.toString());
        System.out.println("setUpConferenceForm: " + request.toString());
        return ResponseEntity.ok(authService.setUpConference(request));
    }

    // 来自 localhost:80/admin 的请求
    @PostMapping("/admin")
    // 查看申请的会议 or 修改会议状态
    public ResponseEntity<?> getConferences(@RequestBody ConferenceManagementRequest request) {
        logger.debug("getConferences: " + request.toString());
        System.out.println("getConferences: " + request.toString());
        return ResponseEntity.ok(authService.ConferenceManagement(request));
    }

}



