package fudan.se.lab2;

import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.generator.ConferenceGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDate;
import java.util.Set;

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

    @Bean
    public CommandLineRunner dataLoader(UserRepository userRepository, ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                // Create authorities if not exist.
                // 管理员、投稿人、审稿人

                // Create an adminPage if not exists.
                if (userRepository.findByUsername("admin") == null) {
                    User admin = new User(
                            "admin",
                            passwordEncoder.encode("ThisisaAdminPASSWORD123$$软工牛逼"),
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
                            passwordEncoder.encode("superModelRuleTheWorld##~"),
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
                            passwordEncoder.encode("superAIBIGGERMind!@@"),
                            "AIRuleTheWorld",
                            "fudan",
                            "software",
                            "AI@fudan.edu.cn"
                    );
                    userRepository.save(AI);
                }

                // add inner conferences
                Conference AIConference = new Conference(userRepository.findByUsername("AI"), "AI abbr", "AI full name", "AI location", LocalDate.of(2020, 4, 12),
                        LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), "AI introduction");
                AIConference.setStatus(Conference.Status.PASS);

                Conference testRobertConference = new Conference(userRepository.findByUsername("testRobert"), "testRobert abbr", "testRobert full name", "testRobert location", LocalDate.of(2020, 4, 12),
                        LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), "testRobert introduction");
                testRobertConference.setStatus(Conference.Status.PASS);
                testRobertConference.setStage(Conference.Stage.CONTRIBUTION);

                Conference testRobertConference2 = new Conference(userRepository.findByUsername("testRobert"), "testRobert2 abbr", "testRobert2 full name", "testRobert2 location", LocalDate.of(2020, 4, 12),
                        LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), LocalDate.of(2020, 4, 12), "testRobert2 introduction");
                testRobertConference2.setStatus(Conference.Status.PASS);
                testRobertConference2.setStage(Conference.Stage.CONTRIBUTION);

                User tempUser = userRepository.findByUsername("AI");
                tempUser.addConference(AIConference);
                conferenceRepository.save(AIConference);
                userRepository.save(tempUser);

                tempUser = userRepository.findByUsername("testRobert");
                tempUser.addConference(testRobertConference);
                conferenceRepository.save(testRobertConference);
                userRepository.save(tempUser);
//                userRepository.findByUsername("testRobert").addConference(testRobertConference);


                tempUser = userRepository.findByUsername("testRobert");
                tempUser.addConference(testRobertConference2);
                conferenceRepository.save(testRobertConference2);
                userRepository.save(tempUser);
//                userRepository.findByUsername("testRobert").addConference(testRobertConference2);

                Set<User> userSet = UserGenerator.getRandomUsers(20);
                Set<Conference> conferenceSet = ConferenceGenerator.getRandomConferences(20, userSet);
                for (User user : userSet
                ) {
                    userRepository.save(user);
                }
                for (Conference conference : conferenceSet
                ) {
                    conference.setStatus(Conference.Status.PASS);
                    conferenceRepository.save(conference);
                }


            }
        };
    }
}

