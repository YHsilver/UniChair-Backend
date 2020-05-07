package fudan.se.lab2.service.conferencePage;

import fudan.se.lab2.controller.conferencePage.conferenceAbstractPage.request.UserGetPassedConferenceRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.service.UtilityService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConferenceAbstractPageServiceTest {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private JwtTokenUtil tokenUtil;
    private ConferenceAbstractPageService conferenceAbstractPageService;
    @Autowired
    public ConferenceAbstractPageServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                         JwtTokenUtil tokenUtil){
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        conferenceAbstractPageService=new ConferenceAbstractPageService(userRepository,conferenceRepository,tokenUtil);
    }


    @Test
    void getPassedConference() {
        User user= UserGenerator.getRandomUser();
        userRepository.save(user);

        Conference conference= ConferenceGenerator.getRandomConference(user);
        conference.setStatus(Conference.Status.PASS);

        conferenceRepository.save(conference);

        User user2=UserGenerator.getRandomUser();
        userRepository.save(user2);
//
//        Review review = new Review(conference, user2, new HashSet<>());
//        conference.getReviewerSet().add(user2);
//        conference.getReviewerAndPapersMap().add(review);
        conferenceRepository.save(conference);


        UserGetPassedConferenceRequest userGetPassedConferenceRequest_chair=new UserGetPassedConferenceRequest(
                tokenUtil.generateToken(user),
                "Chair",
                0,
                10
        );
      assertEquals(
              UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findAll(),true),
              conferenceAbstractPageService.getPassedConference(userGetPassedConferenceRequest_chair)
      );

        UserGetPassedConferenceRequest userGetPassedConferenceRequest_all=new UserGetPassedConferenceRequest(
                tokenUtil.generateToken(user),
                "all",
                0,
                10
        );
        assertEquals(
                UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findAll(),true),
                conferenceAbstractPageService.getPassedConference(userGetPassedConferenceRequest_all)
        );


        UserGetPassedConferenceRequest userGetPassedConferenceRequest_reviewer=new UserGetPassedConferenceRequest(
                tokenUtil.generateToken(user2),
                "Reviewer",
                0,
                10
        );


        assertEquals(
                UtilityService.getJSONObjectListFromConferenceSet(conferenceRepository.findAll(),true),
                conferenceAbstractPageService.getPassedConference(userGetPassedConferenceRequest_reviewer)
        );

        UserGetPassedConferenceRequest userGetPassedConferenceRequest_author=new UserGetPassedConferenceRequest(
                tokenUtil.generateToken(user),
                "Author",
                0,
                10
        );
        assertEquals(
                UtilityService.getJSONObjectListFromConferenceSet(new HashSet<>(),true),
                conferenceAbstractPageService.getPassedConference(userGetPassedConferenceRequest_author)
        );





    }
}