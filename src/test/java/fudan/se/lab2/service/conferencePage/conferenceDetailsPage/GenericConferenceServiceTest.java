package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetConferenceDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetIdentityRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;

import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GenericConferenceServiceTest {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil tokenUtil;

    private GenericConferenceService genericConferenceService;

    @Autowired
    public GenericConferenceServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                        PaperRepository paperRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        genericConferenceService = new GenericConferenceService(userRepository, conferenceRepository, paperRepository,tokenUtil);
    }

    @Test
    void getConferenceDetails() {
        User chair = UserGenerator.getRandomUser();
        userRepository.save(chair);
        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conferenceRepository.save(conference);
        UserGetConferenceDetailsRequest userGetConferenceDetailsRequest = new UserGetConferenceDetailsRequest(conference.getConferenceId());
        assertEquals(conference.toFullJson(), genericConferenceService.getConferenceDetails(userGetConferenceDetailsRequest));
    }

    @Test
    void submitPaper() {
        User chair = UserGenerator.getRandomUser();
        userRepository.save(chair);
        User author = UserGenerator.getRandomUser();
        userRepository.save(author);

        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference);


        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.pdf",    //filename
                "Hallo World".getBytes()); //content

        UserSubmitPaperRequest userSubmitPaperRequest = new UserSubmitPaperRequest(
                tokenUtil.generateToken(author),
                conference.getConferenceId(),
                conference.getTopics(),
                "title",
                new String[]{"name", "a", "a", "a@eamil.com"},
                "summary",
                mockMultipartFile
        );
        System.out.println(genericConferenceService.submitPaper(userSubmitPaperRequest));
        assertEquals(new ArrayList<>(conferenceRepository.findByConferenceId(conference.getConferenceId()).getAuthorSet()).get(0).toString(), author.toString());


    }

    @Test
    void getIdentity() {
        User chair = UserGenerator.getRandomUser();
        userRepository.save(chair);
        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conferenceRepository.save(conference);

        UserGetIdentityRequest userGetIdentityRequest = new UserGetIdentityRequest(tokenUtil.generateToken(chair), conference.getConferenceId());

        assertEquals("[0,1,1]", genericConferenceService.getIdentity(userGetIdentityRequest));

    }
}