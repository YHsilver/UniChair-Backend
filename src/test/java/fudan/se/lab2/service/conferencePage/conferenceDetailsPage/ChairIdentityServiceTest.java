package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.*;
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
import java.util.ArrayList;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChairIdentityServiceTest {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private JwtTokenUtil tokenUtil;
    private ChairIdentityService chairIdentityService;
    private PaperRepository paperRepository;
    private ReviewRepository reviewRepository;

    @Autowired
    public ChairIdentityServiceTest(UserRepository userRepository, InvitationRepository invitationRepository,
                                    ConferenceRepository conferenceRepository, JwtTokenUtil tokenUtil,
                                    ReviewRepository reviewRepository, PaperRepository paperRepository) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
        this.reviewRepository = reviewRepository;
        this.paperRepository = paperRepository;
        chairIdentityService = new ChairIdentityService(userRepository, invitationRepository, conferenceRepository, paperRepository, reviewRepository, tokenUtil);
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
        conferenceRepository.save(conference);


        File file = new File("testFile.pdf");
        Paper paper = new Paper(conference, author, "title", new String[][]{{"name", "are", "unit", "email@email.com"}},
                "summary", file, conference.getTopics());

        paperRepository.save(paper);

        Review review1 = new Review(conference, reviewer1, conference.getTopics());
        Review review2 = new Review(conference, reviewer2, conference.getTopics());
        Review review3 = new Review(conference, reviewer3, conference.getTopics());

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        conference2.getReviewerSet().add(reviewer1);
        conference2.getReviewerSet().add(reviewer2);
        conference2.getReviewerSet().add(reviewer3);
        conferenceRepository.save(conference2);

        assertEquals("{\"message\":\" Reviewing start!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestTopicRelated));
        assertEquals(Conference.Stage.REVIEWING, conferenceRepository.findByConferenceId(conference.getConferenceId()).getStage());

        assertEquals("{\"message\":\" Reviewing start!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestRandom));
        assertEquals(Conference.Stage.REVIEWING, conferenceRepository.findByConferenceId(conference2.getConferenceId()).getStage());
    }


    @Test
    void searchReviewers() {
        User chair = UserGenerator.getRandomUser();
        chair.setFullName("chair1234");
        User user1 = UserGenerator.getRandomUser();
        user1.setFullName("user1234");
        User user2 = UserGenerator.getRandomUser();
        user2.setFullName("user21234");

        userRepository.save(chair);
        userRepository.save(user1);
        userRepository.save(user2);


        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conferenceRepository.save(conference);

        //add reviewer

        Review review = new Review(conference, user2, conference.getTopics());
        reviewRepository.save(review);

        conference.getReviewerSet().add(user2);
        conferenceRepository.save(conference);


        ChairSearchReviewersRequest chairSearchReviewersRequest = new ChairSearchReviewersRequest(
                tokenUtil.generateToken(chair), conference.getConferenceId(), "1234");
        List<JSONObject> list = new ArrayList<>();
        list.add(user1.toStandardJson());
        assertEquals(list, chairIdentityService.searchReviewers(chairSearchReviewersRequest));


    }

    @Test
    void sendInvitation() {
        User chair = UserGenerator.getRandomUser();
        User user1 = UserGenerator.getRandomUser();


        userRepository.save(chair);
        userRepository.save(user1);

        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conferenceRepository.save(conference);

        ChairSendInvitationRequest chairSendInvitationRequest = new ChairSendInvitationRequest(
                tokenUtil.generateToken(chair),
                conference.getConferenceId(),
                new String[]{user1.getUsername()},
                "message"

        );
        Invitation invitation = new Invitation(conference, chair, user1, "message");


        chairIdentityService.sendInvitation(chairSendInvitationRequest);
        user1 = userRepository.findByUsername(user1.getUsername());
        chairIdentityService.sendInvitation(chairSendInvitationRequest);
        List<Invitation> list = new ArrayList<>(invitationRepository.findByReviewer(user1));
        invitation.setInvitationId(list.get(0).getInvitationId());
        assertEquals(invitation.toString(), list.get(0).toString());

    }

    @Test
    void checkInvitations() {
        User chair = UserGenerator.getRandomUser();

        User user1 = UserGenerator.getRandomUser();


        userRepository.save(chair);
        userRepository.save(user1);

        Conference conference = ConferenceGenerator.getRandomConference(chair);
        conferenceRepository.save(conference);

        ChairSendInvitationRequest chairSendInvitationRequest = new ChairSendInvitationRequest(
                tokenUtil.generateToken(chair),
                conference.getConferenceId(),
                new String[]{user1.getUsername()},
                "message"

        );
        chairIdentityService.sendInvitation(chairSendInvitationRequest);

        Invitation invitation = new Invitation(conference, chair,
                user1, "message");

        chairIdentityService.sendInvitation(chairSendInvitationRequest);
        user1 = userRepository.findByUsername(user1.getUsername());

        List<Invitation> list = new ArrayList<>(invitationRepository.findByReviewer(user1));
        invitation.setInvitationId(list.get(0).getInvitationId());


        ChairCheckInvitationsRequest chairCheckInvitationsRequest = new ChairCheckInvitationsRequest(
                tokenUtil.generateToken(chair), conference.getConferenceId(), Invitation.Status.PENDING
        );
        List<JSONObject> checkList = chairIdentityService.checkInvitations(chairCheckInvitationsRequest);

        assertEquals(1, checkList.size());
        assertEquals(invitation.toStandardJson(), chairIdentityService.checkInvitations(chairCheckInvitationsRequest).get(0));
    }
}