package fudan.se.lab2.controller.conferencePage.conferenceAbstractPage;

import fudan.se.lab2.controller.conferencePage.conferenceAbstractPage.request.UserGetPassedConferenceRequest;
import fudan.se.lab2.service.conferencePage.ConferenceAbstractPageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConferenceAbstractPageController {

    private ConferenceAbstractPageService conferenceAbstractPageService;

    private Logger logger = LoggerFactory.getLogger(ConferenceAbstractPageController.class);

    @Autowired
    public ConferenceAbstractPageController(ConferenceAbstractPageService conferenceAbstractPageService) {
            this.conferenceAbstractPageService = conferenceAbstractPageService;
    }

    @PostMapping("/system/getPassedConference")
    public ResponseEntity<?> handleUserRequest(@RequestBody UserGetPassedConferenceRequest request) {
        logger.debug(request.toString());
        System.out.println(request.toString());
        return ResponseEntity.ok(conferenceAbstractPageService.getPassedConference(request));
    }
}
