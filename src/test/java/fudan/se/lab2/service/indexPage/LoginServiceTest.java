package fudan.se.lab2.service.indexPage;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.LoginAndRegisterException.PasswordNotCorrectException;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LoginServiceTest {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil tokenUtil;
    private LoginService loginService;

    @Autowired
    public LoginServiceTest(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        loginService = new LoginService(userRepository, passwordEncoder, tokenUtil);
    }

    @Test
    void login() {
        User user1 = new User(
                "loginTestUser1",
              passwordEncoder.encode("pass1"),
                "fullname1",
                "unit1",
                "area1",
                "emain1@fudan.edu.cn");


        userRepository.save(user1);


        assertEquals(user1.toStandardJson(), loginService.login(user1.getUsername(), "pass1").get("userDetails"));

        Exception exception = new Exception("init ");
        try {
            loginService.login("", user1.getPassword());
        } catch (UsernameNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof UsernameNotFoundException);

        exception = new Exception("init ");
        try {
            loginService.login(user1.getUsername() + "notauser", user1.getPassword());
        } catch (UsernameNotFoundException e) {
            exception = e;
        }
        assertTrue(exception instanceof UsernameNotFoundException);

        exception = new Exception("init ");
        try {
            loginService.login(user1.getUsername(), user1.getPassword() + "passwordnotcoreect");
        } catch (PasswordNotCorrectException e) {
            exception = e;
        }
        assertTrue(exception instanceof PasswordNotCorrectException);


    }
}