package fudan.se.lab2.service.GeneralService;

import fudan.se.lab2.controller.GetUserDetailsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUserDetailsServiceTest {

    private UserRepository userRepository;
    private JwtTokenUtil tokenUtil;
    private GetUserDetailsService getUserDetailsService;

    @Autowired
    public GetUserDetailsServiceTest(UserRepository userRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.tokenUtil = tokenUtil;
        getUserDetailsService=new GetUserDetailsService(userRepository,tokenUtil);
    }

    @Test
    void getUserDetails() {
        User user = UserGenerator.getRandomUser();
        userRepository.save(user);
         GetUserDetailsRequest getUserDetailsRequest=new GetUserDetailsRequest(tokenUtil.generateToken(user));
         assertEquals(user.toStandardJson(),getUserDetailsService.getUserDetails(getUserDetailsRequest));


    }
}