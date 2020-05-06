package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.*;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.*;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.ReviewRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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

        conference.getPaperSet().add(paper);
        conference.getReviewerAndPapersMap().add(review1);
        conference.getReviewerAndPapersMap().add(review2);
        conference.getReviewerAndPapersMap().add(review3);
        conferenceRepository.save(conference);


        conference2.getReviewerSet().add(reviewer1);
        conference2.getReviewerSet().add(reviewer2);
        conference2.getReviewerSet().add(reviewer3);

        conference2.getPaperSet().add(paper);
        conference2.getReviewerAndPapersMap().add(review1);
        conference2.getReviewerAndPapersMap().add(review2);
        conference2.getReviewerAndPapersMap().add(review3);
        conferenceRepository.save(conference2);


        assertEquals("{\"message\":\" Reviewing start!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestTopicRelated));

        assertEquals("{\"message\":\" Reviewing start!\"}", chairIdentityService.startReviewing(chairStartReviewingRequestRandom));

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
        Review review = new Review(conference, user2, new HashSet<>());
        conference.getReviewerSet().add(user2);
        conference.getReviewerAndPapersMap().add(review);
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
        Invitation newInvitation = new Invitation(conference.getConferenceId(), conference.getConferenceFullName(), chair,
                user1, "message");

        chairIdentityService.sendInvitation(chairSendInvitationRequest);
        user1 = userRepository.findByUsername(user1.getUsername());
        List<Invitation> list = new ArrayList<>(user1.getMyInvitations());
        newInvitation.setInvitationId(list.get(0).getInvitationId());
        assertEquals(newInvitation.toString(), list.get(0).toString());

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

        Invitation newInvitation = new Invitation(conference.getConferenceId(), conference.getConferenceFullName(), chair,
                user1, "message");

        chairIdentityService.sendInvitation(chairSendInvitationRequest);
        user1 = userRepository.findByUsername(user1.getUsername());
        List<Invitation> list = new ArrayList<>(user1.getMyInvitations());
        newInvitation.setInvitationId(list.get(0).getInvitationId());

        ChairCheckInvitationsRequest chairCheckInvitationsRequest = new ChairCheckInvitationsRequest(
                tokenUtil.generateToken(chair), conference.getConferenceId(), Invitation.Status.PENDING
        );
        List<JSONObject> checkList = chairIdentityService.checkInvitations(chairCheckInvitationsRequest);

        assertEquals(1, checkList.size());
        assertEquals(newInvitation.toStandardJson(), chairIdentityService.checkInvitations(chairCheckInvitationsRequest).get(0));
    }
}