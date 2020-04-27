package fudan.se.lab2.controller;

import fudan.se.lab2.service.GeneralService.GetConferenceTopicsService;
import fudan.se.lab2.service.GeneralService.GetUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LBW
 * 这个类控制“认证”，即login 和 register的请求
 * 具体响应内容的类是 AuthService
 */

@RestController
public class AuthController {

    private GetUserDetailsService getUserDetailsService;
    private GetConferenceTopicsService getConferenceTopicsService;

    // 日志
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public AuthController(GetUserDetailsService getUserDetailsService, GetConferenceTopicsService getConferenceTopicsService) {
        this.getUserDetailsService = getUserDetailsService;
        this.getConferenceTopicsService = getConferenceTopicsService;
    }


    // 来自 localhost:80/login 的请求
    @PostMapping("/token")
    public ResponseEntity<?> login(@RequestBody GetUserDetailsRequest request) {
        logger.debug(request.toString());
//        System.out.println(request);
        return ResponseEntity.ok(getUserDetailsService.getUserDetails(request));
    }

    @PostMapping("/getConferenceTopics")
    public ResponseEntity<?> getConferenceTopics(@RequestBody GetConferenceTopicsRequest request) {
        logger.debug(request.toString());
//        System.out.println(request);
        return ResponseEntity.ok(getConferenceTopicsService.getConferenceTopics(request));
    }

    /**
     * This is a function to test your connectivity. (健康测试时，可能会用到它）.
     */
    @GetMapping("/welcome")
    public ResponseEntity<?> welcome() {
        Map<String, String> response = new HashMap<>();
        String message = "Welcome to Uni Conference!";
        response.put("message", message);
        return ResponseEntity.ok(response);
    }
}



