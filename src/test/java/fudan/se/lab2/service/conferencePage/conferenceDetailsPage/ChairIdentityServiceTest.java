package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairStartReviewingRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.domain.conference.Topic;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.ReviewRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChairIdentityServiceTest {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private JwtTokenUtil tokenUtil;
    private ChairIdentityService chairIdentityService;

    private ReviewRepository reviewRepository;

    @Autowired
    public ChairIdentityServiceTest(UserRepository userRepository, InvitationRepository invitationRepository,
                                    ConferenceRepository conferenceRepository, JwtTokenUtil tokenUtil,
                                    ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
        this.reviewRepository = reviewRepository;
        chairIdentityService = new ChairIdentityService(userRepository, invitationRepository, conferenceRepository, tokenUtil);
    }

    @Test
    void changeConferenceStage() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        Conference conference = ConferenceGenerator.getRandomConference(user);
        conference.setStage(Conference.Stage.PREPARATION);
        conferenceRepository.save(conference);
        ChairChangeConferenceStageRequest chairChangeConferenceStageRequest =
                new ChairChangeConferenceStageRequest(tokenUtil.generateToken(user), Conference.Stage.CONTRIBUTION, conference.getConferenceId());
        chairIdentityService.changeConferenceStage(chairChangeConferenceStageRequest);
        assertEquals(Conference.Stage.CONTRIBUTION, conferenceRepository.findByConferenceId(conference.getConferenceId()).getStage());
    }

    @Test
    void startReviewing() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
        User author = UserGenerator.getRandomUser();
        userRepository.save(author);

        Conference conference = ConferenceGenerator.getRandomConference(user);
        conference.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference);

        Conference conference2 = ConferenceGenerator.getRandomConference(user);
        conference2.setStage(Conference.Stage.CONTRIBUTION);
        conferenceRepository.save(conference2);

        User reviewer1 = UserGenerator.getRandomUser();
        User reviewer2 = UserGenerator.getRandomUser();
        User reviewer3 = UserGenerator.getRandomUser();
        userRepository.save(reviewer1);
        userRepository.save(reviewer2);
        userRepository.save(reviewer3);



        Paper paper = new Paper(conference, author, "title", new String[][]{}, "summary", null);
        paper.getTopics().add(conference.findTopic(conference.getTopics()[0]));

        ChairStartReviewingRequest chairStartReviewingRequestTopicRelated = new ChairStartReviewingRequest(
                tokenUtil.generateToken(user), conference.getConferenceId(),
                ChairStartReviewingRequest.Strategy.TOPIC_RELATED);

        ChairStartReviewingRequest chairStartReviewingRequestRandom = new ChairStartReviewingRequest(
                tokenUtil.generateToken(user), conference2.getConferenceId(),
                ChairStartReviewingRequest.Strategy.RANDOM);

        assertEquals("{\"message\":\" Too less PC Members or No paper to review!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestTopicRelated));


        conference.getReviewerSet().add(reviewer1);
        conference.getReviewerSet().add(reviewer2);
        conference.getReviewerSet().add(reviewer3);

        Review review1 = new Review(conference, reviewer1, new HashSet<>());
        Review review2 = new Review(conference, reviewer2, new HashSet<>());
        Review review3 = new Review(conference, reviewer3, new HashSet<>());
//        reviewRepository.save(review1);
//        reviewRepository.save(review2);
//        reviewRepository.save(review3);
        conference.getPaperSet().add(paper);
        conference.getReviewerAndPapersMap().add(review1);
        conference.getReviewerAndPapersMap().add(review2);
        conference.getReviewerAndPapersMap().add(review3);
        conferenceRepository.save(conference);


        System.out.println("size:" + reviewRepository.findReviewsByConference(conference).size());

        //conferenceRepository.save(conference);




        assertEquals("{\"message\":\" Reviewing start!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestTopicRelated));
        //assertEquals("{\"message\":\" Reviewing start!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestRandom));

    }


    @Test
    void searchReviewers() {
    }

    @Test
    void sendInvitation() {
    }

    @Test
    void checkInvitations() {
    }
}