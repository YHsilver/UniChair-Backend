package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.Tester;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorModifyPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorRebuttalResultRequset;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.StringGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.PaperRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthorIdentityServiceTest {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private JwtTokenUtil tokenUtil;
    private AuthorIdentityService authorIdentityService;
    private GenericConferenceService genericConferenceService;
    private Tester tester;

    @Autowired
    public AuthorIdentityServiceTest(Tester tester, UserRepository userRepository, ConferenceRepository conferenceRepository,
                                     PaperRepository paperRepository, JwtTokenUtil tokenUtil, GenericConferenceService genericConferenceService) {
        this.tester = tester;
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        this.genericConferenceService = genericConferenceService;
        authorIdentityService = new AuthorIdentityService(userRepository, conferenceRepository, paperRepository, tokenUtil);
    }

    @Test
    void getMyPapers() throws IOException {
        User chair = UserGenerator.getRandomUser();
        User author = UserGenerator.getRandomUser();
        userRepository.save(chair);
        userRepository.save(author);

        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference);


        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.pdf",    //filename
                "Hallo World".getBytes()); //content

        File file = File.createTempFile(mockMultipartFile.getName(), "pdf");
        mockMultipartFile.transferTo(file);

        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
                "summary", file, conference.getTopics());


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


        AuthorGetMyPapersRequest authorGetMyPapersRequest = new AuthorGetMyPapersRequest(
                tokenUtil.generateToken(author), conference.getConferenceId()
        );


        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
        System.out.println("list:" + list);
        paper.setPaperId(list.get(0).getPaperId());
        paper.setFileName("test.pdf");
        assertEquals(paper.toBriefJson(), authorIdentityService.getMyPapers(authorGetMyPapersRequest).get(0));


    }

    @Test
    void getMyPaperDetails() throws IOException {
        User chair = UserGenerator.getRandomUser();
        User author = UserGenerator.getRandomUser();
        userRepository.save(chair);
        userRepository.save(author);

        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference);


        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.pdf",    //filename
                "Hello World".getBytes()); //content

        File file = File.createTempFile("PA_", ".pdf");
        mockMultipartFile.transferTo(file);

        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
                "summary", file, conference.getTopics());


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

        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
        System.out.println("list:" + list);
        paper.setPaperId(list.get(0).getPaperId());


        AuthorGetMyPaperDetailsRequest authorGetMyPaperDetailsRequest = new AuthorGetMyPaperDetailsRequest(
                tokenUtil.generateToken(author),
                paper.getPaperId()
        );


        JSONObject jsonObject = authorIdentityService.getMyPaperDetails(authorGetMyPaperDetailsRequest);
        JSONObject paperJson = paper.toStandardJson();
        for (Object str : jsonObject.keySet()) {
            if (!str.equals("fileName"))
                assertEquals(paperJson.get(str), jsonObject.get(str));
        }


    }

    @Test
    void modifyPaper() throws IOException {
        User chair = UserGenerator.getRandomUser();
        User author = UserGenerator.getRandomUser();
        userRepository.save(chair);
        userRepository.save(author);

        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference);


        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "test.pdf",    //filename
                "Hallo World".getBytes()); //content

        File file = File.createTempFile("PA_", ".pdf");
        mockMultipartFile.transferTo(file);

        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
                "summary", file, conference.getTopics());


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

        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
        System.out.println("list:" + list);
        paper.setPaperId(list.get(0).getPaperId());


        String [][] paperAuthors=list.get(0).getPaperAuthors();
        String[] authors=new String[paperAuthors.length*4];

        int index=0;
        for (String[] paperAuthor : paperAuthors) {
            for (int j = 0; j < 4; j++) {
                authors[index++] = paperAuthor[j];
            }
        }
        System.out.println(Arrays.toString(authors));
        AuthorModifyPaperRequest authorModifyPaperRequest = new AuthorModifyPaperRequest(
                tokenUtil.generateToken(author),
                paper.getPaperId(),
                paper.getTopics(),
                "new title",
                   authors,
                "new summary",
                mockMultipartFile
        );
        authorIdentityService.modifyPaper(authorModifyPaperRequest);
        list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
        paper.setTitle("new title");
        paper.setSummary("new summary");


        JSONObject jsonObject = list.get(0).toStandardJson();
        JSONObject paperJson = paper.toStandardJson();
        for (Object str : jsonObject.keySet()) {
            if (!str.equals("fileName"))
                assertEquals(paperJson.get(str), jsonObject.get(str));
        }
    }

    @Test
    void sendRebuttal(){
        User chair = tester.getNewUser();
        Conference conference = tester.getNewConference(chair);
        User author = tester.getNewUser();
        //move to Contribution
        tester.startContribution(conference);
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        Paper paper = tester.submitNewPaper(conference, author);
        User[] reviewers = tester.addReviewers(conference, 3);
        //move to reviewing
        assertTrue(tester.startReviewing(conference));
        conference = conferenceRepository.findByConferenceId(conference.getConferenceId());
        tester.reviewerReviewAllPapers(conference, -2);
        tester.reviewerCheckAllPaperReviewed(conference);
        //move to reviewed
        tester.startReviewed(conference);
        //conference = conferenceRepository.findByConferenceId(conference.getConferenceId());c
        String rebuttal = "rebuttal Message" + StringGenerator.getRandomString();
        authorIdentityService.sendRebuttal(new AuthorRebuttalResultRequset(tokenUtil.generateToken(author),
                rebuttal, paper.getPaperId()));
        assertEquals(rebuttal, paperRepository.findByPaperId(paper.getPaperId()).getRebuttal());
    }

}