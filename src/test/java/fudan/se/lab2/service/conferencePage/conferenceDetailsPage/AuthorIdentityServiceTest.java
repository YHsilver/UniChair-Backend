package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorIdentityServiceTest {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil tokenUtil;
    private AuthorIdentityService authorIdentityService;


    @Autowired
    public AuthorIdentityServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                 PaperRepository paperRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        authorIdentityService=new AuthorIdentityService(userRepository,conferenceRepository,paperRepository,tokenUtil);
    }

    @Test
    void getMyPapers() {
        User chair= UserGenerator.getRandomUser();
        User author=UserGenerator.getRandomUser();
        userRepository.save(chair);
        userRepository.save(author);

        Conference conference= ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference);




        conference.getAuthorSet().add(author);

        conferenceRepository.save(conference);
        userRepository.save(author);

        System.out.println("author"+conference.getAuthorSet());






    }

    @Test
    void getMyPaperDetails() {
    }

    @Test
    void modifyPaper() {
    }
}