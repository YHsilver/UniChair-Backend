package fudan.se.lab2.controller.applicationPage;

import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;
import fudan.se.lab2.service.applicationPage.ApplicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApplicationPageController {

    private ApplicationService applicationService;
    private Logger logger = LoggerFactory.getLogger(ApplicationPageController.class);

    @Autowired
    public ApplicationPageController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // user申请会议
    @PostMapping("/system/addConferenceApplication")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserAddConferenceApplicationRequest request) {
        logger.debug(request.toString());
        System.out.println(request);
        return ResponseEntity.ok(applicationService.addConferenceApplication(request));
    }

    // user获取自己申请的会议
    @PostMapping("/system/getConferenceApplications")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetConferenceApplicationsRequest request) {
        logger.debug(request.toString());
        System.out.println(request);
        return ResponseEntity.ok(applicationService.getConferenceApplications(request));
    }
}
