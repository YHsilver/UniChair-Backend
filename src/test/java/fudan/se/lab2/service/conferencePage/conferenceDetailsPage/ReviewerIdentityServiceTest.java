package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairStartReviewingRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetPaperDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerGetPapersRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity.ReviewerSubmitPaperReviewedRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.*;
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
class ReviewerIdentityServiceTest {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private PaperRepository paperRepository;
    private ReviewRepository reviewRepository;
    private JwtTokenUtil tokenUtil;
    private ReviewerIdentityService reviewerIdentityService;
    private GenericConferenceService genericConferenceService;
    private ChairIdentityService chairIdentityService;

    @Autowired
    public ReviewerIdentityServiceTest(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                       PaperRepository paperRepository, ReviewRepository reviewRepository, JwtTokenUtil tokenUtil,
                                       InvitationRepository invitationRepository) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.reviewRepository = reviewRepository;
        this.tokenUtil = tokenUtil;
        this.genericConferenceService = new GenericConferenceService(userRepository, conferenceRepository, paperRepository,tokenUtil);
        this.reviewerIdentityService=new ReviewerIdentityService(userRepository,conferenceRepository,paperRepository,
                reviewRepository,tokenUtil);
        this.chairIdentityService=new ChairIdentityService(userRepository,invitationRepository,conferenceRepository,paperRepository,reviewRepository,tokenUtil);
    }

    @Test
    void getPapers() throws IOException {
        //create a reviewing conference. 3 reviewers and 1 paper
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        User author = UserGenerator.getRandomUser();
        userRepository.save(author);

        Conference conference = ConferenceGenerator.getRandomConference(user);
        conference.setStatus(Conference.Status.PASS);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conference.setTopics(new String[]{"topic1","topic2","topic3"});
        conferenceRepository.save(conference);


        User reviewer1 = UserGenerator.getRandomUser();
        User reviewer2 = UserGenerator.getRandomUser();
        User reviewer3 = UserGenerator.getRandomUser();
        userRepository.save(reviewer1);
        userRepository.save(reviewer2);
        userRepository.save(reviewer3);

        Review review1=new Review(conference,reviewer1,conference.getTopics());
        Review review2=new Review(conference,reviewer2,conference.getTopics());
        Review review3=new Review(conference,reviewer3,conference.getTopics());
        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        conference.getReviewerSet().add(reviewer1);
        conference.getReviewerSet().add(reviewer2);
        conference.getReviewerSet().add(reviewer3);
        conferenceRepository.save(conference);


        //submit a paper
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
        try {
            System.out.println(genericConferenceService.submitPaper(userSubmitPaperRequest));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //start reviewing
        ChairStartReviewingRequest chairStartReviewingRequestTopicRelated = new ChairStartReviewingRequest(
                tokenUtil.generateToken(user), conference.getConferenceId(),
                ChairStartReviewingRequest.Strategy.TOPIC_RELATED);
        chairIdentityService.startReviewing(chairStartReviewingRequestTopicRelated);

        //reviewer1 get papers
        ReviewerGetPapersRequest reviewerGetPapersRequest=new ReviewerGetPapersRequest(
                tokenUtil.generateToken(userRepository.findByUsername(reviewer1.getUsername())),
                conference.getConferenceId()
        );


        File file = File.createTempFile("PA_", ".pdf");
        mockMultipartFile.transferTo(file);


        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "a", "a", "a@eamil.com"}},
                "summary", file, conference.getTopics());
        List<Paper> list = new ArrayList<>(paperRepository.findPapersByAuthor(author));
        System.out.println("list:" + list);
        paper.setPaperId(list.get(0).getPaperId());
        System.out.println("re:"+ reviewerIdentityService.getPapers(reviewerGetPapersRequest));
        System.out.println(reviewRepository.findByReviewId(review1.getReviewId()).getPapers());

        assertEquals(paper.toBriefJson(),reviewerIdentityService.getPapers(reviewerGetPapersRequest).get(0));




        //getPaperDetails test
        ReviewerGetPaperDetailsRequest reviewerGetPaperDetailsRequest=new ReviewerGetPaperDetailsRequest(
                tokenUtil.generateToken(reviewer1),paper.getPaperId());
        JSONObject jsonObject =  reviewerIdentityService.getPaperDetails(reviewerGetPaperDetailsRequest);
        JSONObject paperJson = paper.toStandardJson();

        System.out.println(jsonObject);
        System.out.println(paperJson);
        for (Object str : jsonObject.keySet()) {
            if (!str.equals("fileName")&&!((String)str).contains("review")) {
                assertEquals(paperJson.get(str), jsonObject.get(str));
            }
        }


        //submitPaperReviewed test
        paper=paperRepository.findByPaperId(paper.getPaperId());
        ReviewerSubmitPaperReviewedRequest reviewerSubmitPaperReviewedRequest=new ReviewerSubmitPaperReviewedRequest(
                tokenUtil.generateToken(paper.getReviewers().get(0)),
                paper.getPaperId(),
                1,
                "common",
                "HIGH"
                );

        reviewerIdentityService.submitPaperReviewed(reviewerSubmitPaperReviewedRequest);
        paper=paperRepository.findByPaperId(paper.getPaperId());

        assertEquals(Arrays.toString(new Integer[]{1,null,null}),Arrays.toString(paper.getGrades()));
        assertEquals(Arrays.toString(new String[]{"common",null,null}),Arrays.toString(paper.getComments()));
        assertEquals(Arrays.toString(new String[]{"HIGH",null,null}),Arrays.toString(paper.getConfidences()));

    }

    @Test
    void getPaperDetails() {





    }

    @Test
    void submitPaperReviewed() {


    }
}