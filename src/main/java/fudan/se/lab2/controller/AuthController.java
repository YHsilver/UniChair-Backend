package fudan.se.lab2.controller;

import fudan.se.lab2.controller.request.ConferenceRequest;
import fudan.se.lab2.controller.request.LoginRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
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
 * 这个类大概是用来响应的吧……
 */
@RestController
public class AuthController {

    private AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug("RegistrationForm: " + request.toString());
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug("LoginForm: " + request.toString());
        //ResponseEntity.ok().
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));

    }

    @PostMapping("/setUpConference")
    public ResponseEntity<?> setUpConference(@RequestBody ConferenceRequest request) {
        logger.debug("setUpConferenceForm: " + request.toString());
        return ResponseEntity.ok(authService.setUpConference(request));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
//    @GetMapping("/welcome")
//    public ResponseEntity<?> welcome() {
//        Map<String, String> response = new HashMap<>();
//        String message = "Welcome to 2020 Software Engineering Lab2. ";
//        response.put("message", message);
//        return ResponseEntity.ok(response);
//    }

}



