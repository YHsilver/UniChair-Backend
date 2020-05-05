package fudan.se.lab2.service.adminPage;

import fudan.se.lab2.controller.adminPage.request.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.TopicRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminServiceTest {
    private ConferenceRepository conferenceRepository;
    private TopicRepository topicRepository;
    private AdminService adminService;
    private Conference testConference1;
    private Conference testConference2;
    private Conference testConference3;


    // constructor
    @Autowired
    public AdminServiceTest(ConferenceRepository conferenceRepository, TopicRepository topicRepository, UserRepository userRepository) {
        this.conferenceRepository = conferenceRepository;
        this.topicRepository = topicRepository;
        adminService = new AdminService(conferenceRepository, topicRepository);

        User UserTest1 = new User(
                "UserTest1",
                "UserTest1pass",
                "UserTest1fullName",
                "Test",
                "test",
                "test@fudan.edu.cn"
        );

        //todo  bug
        userRepository.save(UserTest1);


        String[] topicSet = {"testTopic1", "testTopic2"};
        testConference1 = new Conference(userRepository.findByUsername("UserTest1"),
                "UserTest1", "AI full name", "AI location",
                LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12),
                LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12),
                "AI introduction", topicSet);
        testConference2 = new Conference(userRepository.findByUsername("UserTest1"),
                "UserTest1", "AI full name", "AI location",
                LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12),
                LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12),
                "AI introduction", topicSet);
        testConference3 = new Conference(userRepository.findByUsername("UserTest1"),
                "UserTest1", "AI full name", "AI location",
                LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12),
                LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12),
                "AI introduction", topicSet);
        testConference1.setStatus(Conference.Status.PASS);
        testConference1.setConferenceId(1L);
        testConference2.setStatus(Conference.Status.PENDING);
        testConference2.setConferenceId(2L);
        testConference3.setStatus(Conference.Status.REJECT);
        testConference3.setConferenceId(3L);
        this.conferenceRepository.save(testConference1);
        this.conferenceRepository.save(testConference2);
        this.conferenceRepository.save(testConference3);

        System.out.println( conferenceRepository.findByConferenceId(1L).getChairman());
    }


    @Test
    void getConferenceApplications() {

        AdminGetConferenceApplicationsRequest pending_request = new AdminGetConferenceApplicationsRequest(Conference.Status.PENDING);
        AdminGetConferenceApplicationsRequest pass_request = new AdminGetConferenceApplicationsRequest(Conference.Status.PASS);
        AdminGetConferenceApplicationsRequest reject_request = new AdminGetConferenceApplicationsRequest(Conference.Status.REJECT);
        List<JSONObject> list;

        list = adminService.getConferenceApplications(pass_request);
        assertEquals(testConference1.toBriefJson(), list.get(0));
        list = adminService.getConferenceApplications(pending_request);
        assertEquals(testConference2.toBriefJson(), list.get(0));
        list = adminService.getConferenceApplications(reject_request);
        assertEquals(testConference3.toBriefJson(), list.get(0));
    }

    @Test
    void changeConferenceStatus() {

        AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest = new AdminChangeConferenceStatusRequest(1L, Conference.Status.REJECT);
        adminService.changeConferenceStatus(adminChangeConferenceStatusRequest);
        Conference conference = conferenceRepository.findByConferenceId(1L);
        assertEquals(Conference.Status.REJECT, conference.getStatus());


        //todo bug
        AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest2 = new AdminChangeConferenceStatusRequest(1L, Conference.Status.PASS);
        adminService.changeConferenceStatus(adminChangeConferenceStatusRequest2);
        Conference conference2 = conferenceRepository.findByConferenceId(1L);
        assertEquals(Conference.Status.PASS, conference2.getStatus());

        AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest3 = new AdminChangeConferenceStatusRequest(1L, Conference.Status.PENDING);
        adminService.changeConferenceStatus(adminChangeConferenceStatusRequest3);
        Conference conference3 = conferenceRepository.findByConferenceId(1L);
        assertEquals(Conference.Status.PENDING, conference3.getStatus());


    }
}