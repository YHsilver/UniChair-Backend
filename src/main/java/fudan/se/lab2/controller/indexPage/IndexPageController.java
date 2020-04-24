package fudan.se.lab2.controller.indexPage;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.controller.indexPage.request.LoginRequest;
import fudan.se.lab2.service.indexPage.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexPageController {
    private LoginService loginService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public IndexPageController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        logger.debug(request.toString());
        return ResponseEntity.ok(loginService.login(request.getUsername(), request.getPassword()));
    }
}
