package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.auth.LoginRequest;
import fudan.se.lab2.controller.request.auth.RegisterRequest;
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
 * 这个类控制“认证”，即login 和 register的请求
 * 具体响应内容的类是 AuthService
 */

@RestController
public class AuthController {

    private AuthService authService;

    // 日志
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // 来自 localhost:80/register 的请求
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug(request.toString());
//        System.out.println(request.toString());
        return ResponseEntity.ok(authService.register(request));
    }

    // 来自 localhost:80/login 的请求
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug(request.toString());
//        System.out.println(request);
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }
}



