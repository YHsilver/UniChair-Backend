package fudan.se.lab2.service.registerPage;

import fudan.se.lab2.controller.registerPage.request.RegisterRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.LoginAndRegisterException.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterServiceTest {

    private PasswordEncoder passwordEncoder;
    private RegisterService registerService;

    @Autowired
    public RegisterServiceTest(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        registerService = new RegisterService(userRepository, passwordEncoder);
    }

    @Test
    void register() {
        User newUser = new User("user1",
                passwordEncoder.encode("pass1"),
                "fullname1",
                "unit1",
                "area1",
                "emain1@fudan.edu.cn");

        RegisterRequest registerRequest = new RegisterRequest(
                "user1",
                "pass1",
                "fullname1",
                "unit1",
                "area1",
                "emain1@fudan.edu.cn"
        );

        User returnUser = registerService.register(registerRequest);
        newUser.setId(returnUser.getId());
        assertEquals(newUser.toString(), returnUser.toString());
        RegisterRequest alreadyRegistedRequest = new RegisterRequest(
                "user1",
                "pass1",
                "fullname1",
                "unit1",
                "area1",
                "emain1@fudan.edu.cn"
        );
    Exception exception=new Exception("init");
    try{
        registerService.register(alreadyRegistedRequest);
    }catch (UsernameHasBeenRegisteredException e){
        exception=e;
    }
    assertTrue(exception instanceof UsernameHasBeenRegisteredException);
    assertEquals("Username 'user1' has been registered", exception.getMessage());

    }
}