package fudan.se.lab2.service.adminPage;

import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.ReviewRepository;
import fudan.se.lab2.repository.UserRepository;

import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;
    private ReviewRepository reviewRepository;
    private AdminService adminService;


    // constructor
    @Autowired
    public AdminServiceTest(ConferenceRepository conferenceRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.conferenceRepository = conferenceRepository;
        this.userRepository = userRepository;
        this.reviewRepository=reviewRepository;
        adminService = new AdminService(conferenceRepository,reviewRepository);
    }


    @Test
    void getConferenceApplications() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        Conference conference1 = ConferenceGenerator.getRandomConference(user);
        Conference conference2 = ConferenceGenerator.getRandomConference(user);
        Conference conference3 = ConferenceGenerator.getRandomConference(user);
        conference1.setStatus(Conference.Status.PENDING);
        conference2.setStatus(Conference.Status.PASS);
        conference3.setStatus(Conference.Status.REJECT);
        conferenceRepository.save(conference1);
        conferenceRepository.save(conference2);
        conferenceRepository.save(conference3);

        AdminGetConferenceApplicationsRequest null_request = new AdminGetConferenceApplicationsRequest(null);
        AdminGetConferenceApplicationsRequest pending_request = new AdminGetConferenceApplicationsRequest(Conference.Status.PENDING);
        AdminGetConferenceApplicationsRequest pass_request = new AdminGetConferenceApplicationsRequest(Conference.Status.PASS);
        AdminGetConferenceApplicationsRequest reject_request = new AdminGetConferenceApplicationsRequest(Conference.Status.REJECT);
        List<JSONObject> list;


        list = adminService.getConferenceApplications(null_request);
        assertEquals(0, list.size());
        list = adminService.getConferenceApplications(pending_request);
        assertEquals(UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByStatus(Conference.Status.PENDING),true), list);

        list = adminService.getConferenceApplications(pass_request);
        assertEquals(UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByStatus(Conference.Status.PASS),true), list);
        list = adminService.getConferenceApplications(reject_request);
        assertEquals(UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findConferencesByStatus(Conference.Status.REJECT),true), list);

    }

    @Test
    void changeConferenceStatus() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        Conference conference = ConferenceGenerator.getRandomConference(user);
        conferenceRepository.save(conference);

        AdminChangeConferenceStatusRequest adminChangeConferenceStatusNullRequest =
                new AdminChangeConferenceStatusRequest(conference.getConferenceId(), null);
        assertEquals("Invalid Request", adminService.changeConferenceStatus(adminChangeConferenceStatusNullRequest));

        AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest =
                new AdminChangeConferenceStatusRequest(conference.getConferenceId(), Conference.Status.REJECT);
        adminService.changeConferenceStatus(adminChangeConferenceStatusRequest);
        assertEquals(Conference.Status.REJECT, conferenceRepository.findByConferenceId(conference.getConferenceId()).getStatus());

        AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest2 = new AdminChangeConferenceStatusRequest(conference.getConferenceId(), Conference.Status.PASS);
        adminService.changeConferenceStatus(adminChangeConferenceStatusRequest2);
        assertEquals(Conference.Status.PASS, conferenceRepository.findByConferenceId(conference.getConferenceId()).getStatus());

        AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest3 = new AdminChangeConferenceStatusRequest(conference.getConferenceId(), Conference.Status.PENDING);
        adminService.changeConferenceStatus(adminChangeConferenceStatusRequest3);
        assertEquals(Conference.Status.PENDING, conferenceRepository.findByConferenceId(conference.getConferenceId()).getStatus());


    }
}