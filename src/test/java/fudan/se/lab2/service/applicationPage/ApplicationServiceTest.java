package fudan.se.lab2.service.applicationPage;

import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.exception.ConferencException.IllegalConferenceApplicationException;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.adminPage.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationServiceTest {
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;
    private ApplicationService applicationService;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public ApplicationServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                  JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        applicationService=new ApplicationService(userRepository,conferenceRepository,tokenUtil);
    }




    @Test
    void getConferenceApplications() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        Conference conference = ConferenceGenerator.getRandomConference(user);
        conference.setStatus(Conference.Status.PENDING);
        conferenceRepository.save(conference);

        User user2 = UserGenerator.getRandomUser();
        userRepository.save(user2);
        Conference conference2= ConferenceGenerator.getRandomConference(user2);
        conference2.setStatus(Conference.Status.PENDING);
        conferenceRepository.save(conference2);


        UserGetConferenceApplicationsRequest userGetConferenceApplicationsRequest=
                new UserGetConferenceApplicationsRequest(tokenUtil.generateToken(user),Conference.Status.PENDING);
        assertEquals(conference.toBriefJson(),applicationService.getConferenceApplications(userGetConferenceApplicationsRequest).get(0));

    }

    @Test
    void addConferenceApplication() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        IllegalConferenceApplicationException ex=new IllegalConferenceApplicationException("init");
        Conference legalConference = ConferenceGenerator.getRandomConference(user);

        UserAddConferenceApplicationRequest legalUserAddConferenceApplicationRequest=
                new UserAddConferenceApplicationRequest(
                        tokenUtil.generateToken(user),
                        legalConference.getConferenceAbbreviation(),
                        legalConference.getConferenceFullName(),
                        legalConference.getConferenceTime(),
                        legalConference.getConferenceLocation(),
                        legalConference.getContributeStartTime(),
                        legalConference.getContributeEndTime(),
                        legalConference.getResultReleaseTime(),
                        legalConference.getIntroduction(),
                        legalConference.getTopics()
                );
        assertEquals("{\"message\":\"conference application submit success!\"}",
                applicationService.addConferenceApplication(legalUserAddConferenceApplicationRequest));


        UserAddConferenceApplicationRequest illegalTimeUserAddConferenceApplicationRequest=
                new UserAddConferenceApplicationRequest(
                        tokenUtil.generateToken(user),
                        legalConference.getConferenceAbbreviation(),
                        legalConference.getConferenceFullName(),
                        legalConference.getConferenceTime(),
                        legalConference.getConferenceLocation(),
                        legalConference.getContributeEndTime(),
                        legalConference.getContributeStartTime(),
                        legalConference.getResultReleaseTime(),
                        legalConference.getIntroduction(),
                        legalConference.getTopics()
                );

        try {
            applicationService.addConferenceApplication(illegalTimeUserAddConferenceApplicationRequest);
        } catch (IllegalConferenceApplicationException e) {
            ex=e;
        }
        assertEquals("Time is not well ordered",ex.getMessage());


        String[] emptyTopic={""};
        UserAddConferenceApplicationRequest illegalTopicUserAddConferenceApplicationRequest=
                new UserAddConferenceApplicationRequest(
                        tokenUtil.generateToken(user),
                        legalConference.getConferenceAbbreviation(),
                        legalConference.getConferenceFullName(),
                        legalConference.getConferenceTime(),
                        legalConference.getConferenceLocation(),
                        legalConference.getContributeStartTime(),
                        legalConference.getContributeEndTime(),
                        legalConference.getResultReleaseTime(),
                        legalConference.getIntroduction(),
                        emptyTopic
                );


        try {
            ex=new IllegalConferenceApplicationException("init");
            applicationService.addConferenceApplication(illegalTopicUserAddConferenceApplicationRequest);
        } catch (IllegalConferenceApplicationException e) {
            ex=e;
        }
        assertEquals("Required topic information missing!",ex.getMessage());

        UserAddConferenceApplicationRequest illegalMissingUserAddConferenceApplicationRequest=
                new UserAddConferenceApplicationRequest(
                        tokenUtil.generateToken(user),
                        legalConference.getConferenceAbbreviation(),
                        "",
                        legalConference.getConferenceTime(),
                        legalConference.getConferenceLocation(),
                        legalConference.getContributeStartTime(),
                        legalConference.getContributeEndTime(),
                        legalConference.getResultReleaseTime(),
                        legalConference.getIntroduction(),
                        legalConference.getTopics()
                );

        try {
            ex=new IllegalConferenceApplicationException("init");
            applicationService.addConferenceApplication(illegalMissingUserAddConferenceApplicationRequest);
        } catch (IllegalConferenceApplicationException e) {
            ex=e;
        }
        assertEquals("Required information missing!",ex.getMessage());
    }
}