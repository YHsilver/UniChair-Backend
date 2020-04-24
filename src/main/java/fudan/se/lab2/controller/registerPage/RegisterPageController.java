package fudan.se.lab2.controller.registerPage;

import fudan.se.lab2.controller.AuthController;
import fudan.se.lab2.controller.registerPage.request.RegisterRequest;
import fudan.se.lab2.service.registerPage.RegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterPageController {

    private RegisterService registerService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    public RegisterPageController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        logger.debug(request.toString());
        return ResponseEntity.ok(registerService.register(request));
    }
}
