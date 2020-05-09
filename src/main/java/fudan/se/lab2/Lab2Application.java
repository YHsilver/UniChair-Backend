package fudan.se.lab2;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Welcome to 2020 Software Engineering Lab2.
 * This is your first lab to write your own code and build a spring boot application.
 * Enjoy it :)
 *
 * @author LBW
 * 初始化
 */

@SpringBootApplication
public class Lab2Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

    /**
     * This is a function to create some basic entities when the application starts.
     * Now we are using a In-Memory database, so you need it.
     * You can change it as you like.
     */

//    @Bean
//    public CommandLineRunner dataLoader(UserRepository userRepository,
//                                        ConferenceRepository conferenceRepository,
//                                        PasswordEncoder passwordEncoder,
//                                        PaperRepository paperRepository,
//                                        InvitationRepository invitationRepository,
//                                        ReviewRepository reviewRepository,
//                                        JwtTokenUtil tokenUtil) {
    public CommandLineRunner dataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Create authorities if not exist.
            // 管理员、投稿人、审稿人
//
//                try {
//                    UtilityService.random = SecureRandom.getInstanceStrong();
//                } catch (NoSuchAlgorithmException e) {
//                    UtilityService.random = new Random();
//                }

            // Create an adminPage if not exists.
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User(
                        "admin",
                        passwordEncoder.encode("adminPass"),
                        "admin",
                        "fudan",
                        "software",
                        "adminPage@fudan.edu.cn"
                );
                userRepository.save(admin);
            }

            // Create testRobert if not exists.
            if (userRepository.findByUsername("testRobert") == null) {
                User testRobert = new User(
                        "testRobert",
                        passwordEncoder.encode("testRobertPass"),
                        "testRobertRuleTheWorld",
                        "fudan",
                        "software",
                        "testRobert@fudan.edu.cn"
                );
                userRepository.save(testRobert);
            }

            // Create testRobert if not exists.
            if (userRepository.findByUsername("AI") == null) {
                User AI = new User(
                        "AI",
                        passwordEncoder.encode("AIPass"),
                        "AIRuleTheWorld",
                        "fudan",
                        "software",
                        "AI@fudan.edu.cn"
                );
                userRepository.save(AI);
            }

//            if (userRepository.findByUsername("robert1") == null) {
//                User AI = new User(
//                        "robert1",
//                        passwordEncoder.encode("pass"),
//                        "robert1",
//                        "fudan",
//                        "software",
//                        "AI@fudan.edu.cn"
//                );
//                userRepository.save(AI);
//            }
//            if (userRepository.findByUsername("robert2") == null) {
//                User AI = new User(
//                        "robert2",
//                        passwordEncoder.encode("pass"),
//                        "robert2",
//                        "fudan",
//                        "software",
//                        "AI@fudan.edu.cn"
//                );
//                userRepository.save(AI);
//            }
//
//            if (userRepository.findByUsername("robert3") == null) {
//                User AI = new User(
//                        "robert3",
//                        passwordEncoder.encode("pass"),
//                        "robert3",
//                        "fudan",
//                        "software",
//                        "AI@fudan.edu.cn"
//                );
//                userRepository.save(AI);
//            }
//
//            String[] topicSet = new String[]{"AI", "Robert"};
//            // add inner conferences
//            Conference AIConference = new Conference(userRepository.findByUsername("AI"), "AI abbr", "AI full name", "AI location", LocalDate.of(2020, 4, 12),
//                    LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), "AI introduction", topicSet);
//            AIConference.setTopics(new String[]{"AI TOPIC1", "AI TOPIC2"});
//            AIConference.setStatus(Conference.Status.PASS);
//            AIConference.setStage(Conference.Stage.CONTRIBUTION);
//
//            Conference testRobertConference = new Conference(userRepository.findByUsername("testRobert"), "testRobert abbr", "testRobert full name", "testRobert location", LocalDate.of(2020, 4, 12),
//                    LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), "testRobert introduction", topicSet);
//            testRobertConference.setStatus(Conference.Status.PASS);
//            testRobertConference.setTopics(new String[]{"RO TOPIC1", "RO TOPIC2"});
//            testRobertConference.setStage(Conference.Stage.CONTRIBUTION);
//
//            Conference testRobertConference2 = new Conference(userRepository.findByUsername("testRobert"), "testRobert2 abbr", "testRobert2 full name", "testRobert2 location", LocalDate.of(2020, 4, 12),
//                    LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), "testRobert2 introduction", topicSet);
//            testRobertConference2.setStatus(Conference.Status.PASS);
//            testRobertConference2.setTopics(new String[]{"RO TOPIC1", "RO TOPIC2"});
//            testRobertConference2.setStage(Conference.Stage.CONTRIBUTION);
//
//            User tempUser = userRepository.findByUsername("AI");
//            conferenceRepository.save(AIConference);
//            userRepository.save(tempUser);
//
//            tempUser = userRepository.findByUsername("testRobert");
//            conferenceRepository.save(testRobertConference);
//            userRepository.save(tempUser);
////                userRepository.findByUsername("testRobert").addConference(testRobertConference);
//
//
//            tempUser = userRepository.findByUsername("testRobert");
//            conferenceRepository.save(testRobertConference2);
//            userRepository.save(tempUser);
//
//            Set<User> userSet = UserGenerator.getRandomUsers(20);
//            Set<Conference> conferenceSet = ConferenceGenerator.getRandomConferences(20, userSet);
//            for (User user : userSet
//            ) {
//                userRepository.save(user);
//            }
//            for (Conference conference : conferenceSet
//            ) {
//                conference.setStatus(Conference.Status.PASS);
//                conferenceRepository.save(conference);
//            }
//            // send invitations
//            ChairIdentityService chairIdentityService = new ChairIdentityService(userRepository,
//                    invitationRepository, conferenceRepository, paperRepository, reviewRepository, tokenUtil);
//
//            ChairSendInvitationRequest chairSendInvitationRequest = new ChairSendInvitationRequest(
//                    tokenUtil.generateToken(userRepository.findByUsername("AI")),
//                    AIConference.getConferenceId(),
//                    new String[]{"robert1", "robert2", "robert3"},
//                    "message"
//
//            );
//            chairIdentityService.sendInvitation(chairSendInvitationRequest);
//
//            // become PC Member
//            MessageService messageService = new MessageService(userRepository, invitationRepository, conferenceRepository, reviewRepository, tokenUtil);
//            UserDecideInvitationsRequest userDecideInvitationsRequest1 = new UserDecideInvitationsRequest(
//                    tokenUtil.generateToken(userRepository.findByUsername("robert1")),
//                    new ArrayList<>(invitationRepository.findByReviewer(userRepository.findByUsername("robert1"))).get(0).getInvitationId(),
//                    Invitation.Status.PASS,
//                    AIConference.getTopics()
//            );
//            messageService.userDecideInvitations(userDecideInvitationsRequest1);
//            UserDecideInvitationsRequest userDecideInvitationsRequest2 = new UserDecideInvitationsRequest(
//                    tokenUtil.generateToken(userRepository.findByUsername("robert2")),
//                    new ArrayList<>(invitationRepository.findByReviewer(userRepository.findByUsername("robert2"))).get(0).getInvitationId(),
//                    Invitation.Status.PASS,
//                    AIConference.getTopics()
//            );
//            messageService.userDecideInvitations(userDecideInvitationsRequest2);
//            UserDecideInvitationsRequest userDecideInvitationsRequest3 = new UserDecideInvitationsRequest(
//                    tokenUtil.generateToken(userRepository.findByUsername("robert3")),
//                    new ArrayList<>(invitationRepository.findByReviewer(userRepository.findByUsername("robert3"))).get(0).getInvitationId(),
//                    Invitation.Status.PASS,
//                    AIConference.getTopics()
//            );
//            messageService.userDecideInvitations(userDecideInvitationsRequest3);

        };
    }
}

