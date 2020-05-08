//package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;
//
//import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPaperDetailsRequest;
//import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorGetMyPapersRequest;
//import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity.AuthorModifyPaperRequest;
//import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
//import fudan.se.lab2.domain.User;
//import fudan.se.lab2.domain.conference.Conference;
//import fudan.se.lab2.domain.conference.Paper;
//import fudan.se.lab2.generator.ConferenceGenerator;
//import fudan.se.lab2.generator.UserGenerator;
//import fudan.se.lab2.repository.ConferenceRepository;
//import fudan.se.lab2.repository.PaperRepository;
//import fudan.se.lab2.repository.UserRepository;
//import fudan.se.lab2.security.jwt.JwtTokenUtil;
//import org.json.simple.JSONObject;
//import org.junit.jupiter.api.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class AuthorIdentityServiceTest {
//
//    private UserRepository userRepository;
//    private ConferenceRepository conferenceRepository;
//    private PaperRepository paperRepository;
//    private JwtTokenUtil tokenUtil;
//    private AuthorIdentityService authorIdentityService;
//    private GenericConferenceService genericConferenceService;
//
//
//    @Autowired
//    public AuthorIdentityServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
//                                     PaperRepository paperRepository, JwtTokenUtil tokenUtil, GenericConferenceService genericConferenceService) {
//        this.userRepository = userRepository;
//        this.paperRepository = paperRepository;
//        this.conferenceRepository = conferenceRepository;
//        this.tokenUtil = tokenUtil;
//        this.genericConferenceService = genericConferenceService;
//        authorIdentityService = new AuthorIdentityService(userRepository, conferenceRepository, paperRepository, tokenUtil);
//    }
//
//    @Test
//    void getMyPapers() throws IOException {
//        User chair = UserGenerator.getRandomUser();
//        User author = UserGenerator.getRandomUser();
//        userRepository.save(chair);
//        userRepository.save(author);
//
//        Conference conference = ConferenceGenerator.getRandomConference(chair);
//        conference.setStatus(Conference.Status.PASS);
//        conference.setStage(Conference.Stage.CONTRIBUTION);
//        conferenceRepository.save(conference);
//
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "test.pdf",    //filename
//                "Hallo World".getBytes()); //content
//
//        File file = File.createTempFile(mockMultipartFile.getName(), "pdf");
//        mockMultipartFile.transferTo(file);
//
//        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
//                "summary", file, conference.getTopics());
//
//
//        UserSubmitPaperRequest userSubmitPaperRequest = new UserSubmitPaperRequest(
//                tokenUtil.generateToken(author),
//                conference.getConferenceId(),
//                conference.getTopics(),
//                "title",
//                new String[]{"name", "a", "a", "a@eamil.com"},
//                "summary",
//                mockMultipartFile
//        );
//        try {
//            System.out.println(genericConferenceService.submitPaper(userSubmitPaperRequest));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        AuthorGetMyPapersRequest authorGetMyPapersRequest = new AuthorGetMyPapersRequest(
//                tokenUtil.generateToken(author), conference.getConferenceId()
//        );
//
//
//        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
//        System.out.println("list:" + list);
//        paper.setPaperId(list.get(0).getPaperId());
//
//        assertEquals(paper.toBriefJson(), authorIdentityService.getMyPapers(authorGetMyPapersRequest).get(0));
//
//
//    }
//
//    @Test
//    void getMyPaperDetails() throws IOException {
//        User chair = UserGenerator.getRandomUser();
//        User author = UserGenerator.getRandomUser();
//        userRepository.save(chair);
//        userRepository.save(author);
//
//        Conference conference = ConferenceGenerator.getRandomConference(chair);
//        conference.setStatus(Conference.Status.PASS);
//        conference.setStage(Conference.Stage.CONTRIBUTION);
//        conferenceRepository.save(conference);
//
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "test.pdf",    //filename
//                "Hello World".getBytes()); //content
//
//        File file = File.createTempFile("PA_", ".pdf");
//        mockMultipartFile.transferTo(file);
//
//        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
//                "summary", file, conference.getTopics());
//
//
//        UserSubmitPaperRequest userSubmitPaperRequest = new UserSubmitPaperRequest(
//                tokenUtil.generateToken(author),
//                conference.getConferenceId(),
//                conference.getTopics(),
//                "title",
//                new String[]{"name", "a", "a", "a@eamil.com"},
//                "summary",
//                mockMultipartFile
//        );
//        try {
//            System.out.println(genericConferenceService.submitPaper(userSubmitPaperRequest));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
//        System.out.println("list:" + list);
//        paper.setPaperId(list.get(0).getPaperId());
//
//
//        AuthorGetMyPaperDetailsRequest authorGetMyPaperDetailsRequest = new AuthorGetMyPaperDetailsRequest(
//                tokenUtil.generateToken(author),
//                paper.getPaperId()
//        );
//
//
//        JSONObject jsonObject = authorIdentityService.getMyPaperDetails(authorGetMyPaperDetailsRequest);
//        JSONObject paperJson = paper.toStandardJson();
//        for (Object str : jsonObject.keySet()) {
//            if (!str.equals("fileName"))
//                assertEquals(paperJson.get(str), jsonObject.get(str));
//        }
//
//
//    }
//
//    @Test
//    void modifyPaper() throws IOException {
//        User chair = UserGenerator.getRandomUser();
//        User author = UserGenerator.getRandomUser();
//        userRepository.save(chair);
//        userRepository.save(author);
//
//        Conference conference = ConferenceGenerator.getRandomConference(chair);
//        conference.setStatus(Conference.Status.PASS);
//        conference.setStage(Conference.Stage.CONTRIBUTION);
//        conferenceRepository.save(conference);
//
//
//        MockMultipartFile mockMultipartFile = new MockMultipartFile(
//                "test.pdf",    //filename
//                "Hallo World".getBytes()); //content
//
//        File file = File.createTempFile("PA_", ".pdf");
//        mockMultipartFile.transferTo(file);
//
//        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
//                "summary", file, conference.getTopics());
//
//
//        UserSubmitPaperRequest userSubmitPaperRequest = new UserSubmitPaperRequest(
//                tokenUtil.generateToken(author),
//                conference.getConferenceId(),
//                conference.getTopics(),
//                "title",
//                new String[]{"name", "a", "a", "a@eamil.com"},
//                "summary",
//                mockMultipartFile
//        );
//        try {
//            System.out.println(genericConferenceService.submitPaper(userSubmitPaperRequest));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
//        System.out.println("list:" + list);
//        paper.setPaperId(list.get(0).getPaperId());
//
//
//        AuthorModifyPaperRequest authorModifyPaperRequest = new AuthorModifyPaperRequest(
//                tokenUtil.generateToken(author),
//                paper.getPaperId(),
//                paper.getTopics(),
//                "new title",
//                paper.getPaperAuthors(),
//                "new summary",
//                mockMultipartFile
//        );
//        authorIdentityService.modifyPaper(authorModifyPaperRequest);
//        list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
//        paper.setTitle("new title");
//        paper.setSummary("new summary");
//
//
//        JSONObject jsonObject = list.get(0).toStandardJson();
//        JSONObject paperJson = paper.toStandardJson();
//        for (Object str : jsonObject.keySet()) {
//            if (!str.equals("fileName"))
//                assertEquals(paperJson.get(str), jsonObject.get(str));
//        }
//    }
//}