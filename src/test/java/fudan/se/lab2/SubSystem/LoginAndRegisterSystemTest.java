package fudan.se.lab2.SubSystem;

import fudan.se.lab2.Tester;
import fudan.se.lab2.controller.indexPage.request.LoginRequest;
import fudan.se.lab2.controller.registerPage.request.RegisterRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.LoginAndRegisterException.PasswordNotCorrectException;
import fudan.se.lab2.exception.LoginAndRegisterException.UsernameHasBeenRegisteredException;
import fudan.se.lab2.generator.StringGenerator;
import fudan.se.lab2.generator.UserGenerator;
import fudan.se.lab2.service.indexPage.LoginService;
import fudan.se.lab2.service.registerPage.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class LoginAndRegisterSystemTest {
    /*
    * 注册与登录是系统中功能封闭的子系统，同时也是其他子系统和功能的基础
    * */

    private Tester tester;

    @Autowired
    public LoginAndRegisterSystemTest(Tester tester){
        this.tester = tester;
    }

    @Test
    void systemTest(){
        // 得到与子系统相关的 Service 实例，包括 LoginService, RegisterService
        LoginService loginService = tester.loginService;
        RegisterService registerService = tester.registerService;

        /*
        * 对登录和注册组合的情况进行测试
        * 1、注册成功后，该用户用正确的用户名密码登录，登录成功
        * 2、注册成功后，该用户用正确的用户民和错误的密码登录，登录失败
        * 3、用户用不存在的用户名登录，登录失败
        * 4、用已存在的用户名注册，注册失败
        * */

        User user = UserGenerator.getRandomUser();
        // 注册一个 user
        RegisterRequest registerRequest = new RegisterRequest(user.getUsername(), user.getPassword(),
                user.getFullName(), user.getUnit(), user.getArea(), user.getEmail());
        registerService.register(registerRequest);
        // 该 user 登录，登录成功
        assertNotNull(loginService.login(user.getUsername(), user.getPassword()));
        // 用错误的密码登录，登录失败
        Exception passNotCorrectException = new Exception("init");
        try{
            loginService.login(user.getUsername(), user.getPassword() + StringGenerator.getRandomString());
        }catch (PasswordNotCorrectException e){
            passNotCorrectException = e;
        }
        assertTrue(passNotCorrectException instanceof PasswordNotCorrectException);
        // 用不存在的用户名登录，登录失败
        Exception userNameNotFoundException = new Exception("init");
        try{
            loginService.login(user.getUsername() + StringGenerator.getRandomString(), user.getPassword());
        }catch (UsernameNotFoundException e){
            userNameNotFoundException = e;
        }
        assertTrue(userNameNotFoundException instanceof UsernameNotFoundException);
        // 用注册过的用户名注册
        Exception usernameHasBeenReigsteredException = new Exception("init");
        try{
            registerService.register(registerRequest);
        }catch (UsernameHasBeenRegisteredException e){
            usernameHasBeenReigsteredException = e;
        }
        assertTrue(usernameHasBeenReigsteredException instanceof UsernameHasBeenRegisteredException);
    }

}
