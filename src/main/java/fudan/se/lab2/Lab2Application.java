package fudan.se.lab2;

import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;

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
    public CommandLineRunner dataLoader(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                // Create authorities if not exist.
                // 管理员、投稿人、审稿人
                Authority adminAuthority = getOrCreateAuthority("Admin", authorityRepository);
                Authority contributorAuthority = getOrCreateAuthority("Contributor", authorityRepository);
                Authority reviewerAuthority = getOrCreateAuthority("Reviewer", authorityRepository);

                // Create an admin if not exists.
                if (userRepository.findByUsername("admin") == null) {
                    User admin = new User(
                            "admin",
                            passwordEncoder.encode("ThisisaAdminPASSWORD123$$软工牛逼"),
                            "admin",
                            "fudan",
                            "software",
                            "admin@fudan.edu.cn",
                            new HashSet<>(Collections.singletonList(adminAuthority))
                    );
                    userRepository.save(admin);
                }

                Authority testRobertAuthority = getOrCreateAuthority("testRobert", authorityRepository);

                // Create testRobert if not exists.
                if (userRepository.findByUsername("testRobert") == null) {
                    User testRobert = new User(
                            "testRobert",
                            passwordEncoder.encode("superModelRuleTheWorld##~"),
                            "testRobertRuleTheWorld",
                            "fudan",
                            "software",
                            "testRobert@fudan.edu.cn",
                            new HashSet<>(Collections.singletonList(testRobertAuthority))
                    );
                    userRepository.save(testRobert);
                }

                Authority AIAuthority = getOrCreateAuthority("AI", authorityRepository);

                // Create testRobert if not exists.
                if (userRepository.findByUsername("AI") == null) {
                    User AI = new User(
                            "AI",
                            passwordEncoder.encode("superAIBIGGERMind!@@"),
                            "AIRuleTheWorld",
                            "fudan",
                            "software",
                            "AI@fudan.edu.cn",
                            new HashSet<>(Collections.singletonList(AIAuthority))
                    );
                    userRepository.save(AI);
                }
            }

            private Authority getOrCreateAuthority(String authorityText, AuthorityRepository authorityRepository) {
                Authority authority = authorityRepository.findByAuthority(authorityText);
                if (authority == null) {
                    authority = new Authority(authorityText);
                    authorityRepository.save(authority);
                }
                return authority;
            }
        };
    }
}

