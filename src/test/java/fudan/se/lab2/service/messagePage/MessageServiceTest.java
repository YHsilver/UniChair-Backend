package fudan.se.lab2.service.messagePage;

import fudan.se.lab2.controller.messagePage.request.UserCheckMyInvitationsRequest;
import fudan.se.lab2.controller.messagePage.request.UserDecideInvitationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MessageServiceTest {
    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private ReviewRepository reviewRepository;
    private TopicRepository topicRepository;
    private JwtTokenUtil tokenUtil;
    private MessageService messageService;

    @Autowired
    public MessageServiceTest(UserRepository userRepository, InvitationRepository invitationRepository, TopicRepository topicRepository,
                          ConferenceRepository conferenceRepository, ReviewRepository reviewRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.topicRepository = topicRepository;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
        this.reviewRepository = reviewRepository;
        messageService=new MessageService(userRepository,invitationRepository,conferenceRepository,reviewRepository,tokenUtil);
    }


    @Test
    void userCheckMyInvitations() {
        User chair= UserGenerator.getRandomUser();
        User user1=UserGenerator.getRandomUser();
        userRepository.save(chair);
        userRepository.save(user1);

        Conference conference= ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conferenceRepository.save(conference);

        Invitation newInvitation = new Invitation(conference.getConferenceId(), conference.getConferenceFullName(), chair,
                user1, "message");
        invitationRepository.save(newInvitation);
        chair.getSendInvitations().add(newInvitation);
        user1.getMyInvitations().add(newInvitation);
        userRepository.save(user1);
        userRepository.save(chair);

        UserCheckMyInvitationsRequest userCheckMyInvitationsRequest=new UserCheckMyInvitationsRequest(
                tokenUtil.generateToken(user1),Invitation.Status.PENDING
        );
       assertEquals(newInvitation.toStandardJson(),
               messageService.userCheckMyInvitations(userCheckMyInvitationsRequest).get(0));





    }

    @Test
    void userDecideInvitations() {
        User chair= UserGenerator.getRandomUser();
        User user1=UserGenerator.getRandomUser();
        userRepository.save(chair);
        userRepository.save(user1);

        Conference conference= ConferenceGenerator.getRandomConference(chair);
        conference.setStatus(Conference.Status.PASS);
        conferenceRepository.save(conference);
        for (String topic: conference.getTopics()
             ) {
            Topic tmpTopic = new Topic(conference, topic);
            conference.getTopicSet().add(tmpTopic);
            topicRepository.save(tmpTopic);
        }
        conferenceRepository.save(conference);

        Invitation newInvitation = new Invitation(conference.getConferenceId(), conference.getConferenceFullName(), chair,
                user1, "message");
        invitationRepository.save(newInvitation);
        chair.getSendInvitations().add(newInvitation);
        user1.getMyInvitations().add(newInvitation);
        userRepository.save(user1);
        userRepository.save(chair);


        String [] topics={conference.getTopics()[0]};

        UserDecideInvitationsRequest userDecideInvitationsRequest=new UserDecideInvitationsRequest(
                tokenUtil.generateToken(user1),
                newInvitation.getInvitationId(),
                Invitation.Status.PASS,
                 topics
        );
        messageService.userDecideInvitations(userDecideInvitationsRequest);

        assertEquals(Invitation.Status.PASS, invitationRepository.findByInvitationId(newInvitation.getInvitationId()).getStatus());
        assertEquals(1, conferenceRepository.findByConferenceId(conference.getConferenceId()).getReviewerSet().size());



    }
}