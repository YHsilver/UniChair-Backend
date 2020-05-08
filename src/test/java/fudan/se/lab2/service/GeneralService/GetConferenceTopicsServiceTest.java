package fudan.se.lab2.service.GeneralService;

import fudan.se.lab2.controller.GetConferenceTopicsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetConferenceTopicsServiceTest {
    private ConferenceRepository conferenceRepository;
    private UserRepository userRepository;
    private GetConferenceTopicsService getConferenceTopicsService;


    @Autowired
    public GetConferenceTopicsServiceTest(ConferenceRepository conferenceRepository, UserRepository userRepository){
        this.conferenceRepository = conferenceRepository;
        this.userRepository=userRepository;
        getConferenceTopicsService=new GetConferenceTopicsService(conferenceRepository);
    }


    @Test
    void getConferenceTopics() {
        User user= UserGenerator.getRandomUser();
        userRepository.save(user);
        Conference conference= ConferenceGenerator.getRandomConference(user);
        conferenceRepository.save(conference);
        GetConferenceTopicsRequest  getConferenceTopicsRequest=new GetConferenceTopicsRequest(conference.getConferenceId());
        assertEquals(Arrays.toString(conference.getTopics()),Arrays.toString(getConferenceTopicsService.getConferenceTopics(getConferenceTopicsRequest)));
    }
}