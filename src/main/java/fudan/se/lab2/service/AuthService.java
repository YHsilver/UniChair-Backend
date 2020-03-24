package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ConferenceRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.exception.LoginAndRegisterException.PasswordNotCorrectException;
import fudan.se.lab2.exception.LoginAndRegisterException.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author LBW
 */
@Service

public class AuthService {
    private UserRepository userRepository;
    private AuthorityRepository authorityRepository;
    private ConferenceRepository conferenceRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    public User register(RegisterRequest request) {
        // TODO: Implement the function.
        String username = request.getUsername();
        String password = request.getPassword();
        String fullname = request.getFullname();
        User existUser = userRepository.findByUsername(username);
        if (existUser != null) {
            throw new UsernameHasBeenRegisteredException(username);
        }
        User newUser = new User(username, passwordEncoder.encode(password), fullname, new HashSet<>());
        userRepository.save(newUser);
        return newUser;
    }

    public Map<String, Object> login(String username, String rawPassword) {
        // TODO: Implement the function.
        //tokenUtil.generateToken()
        if(passwordEncoder == null){
            System.out.println("PasswordEncoder NULL");
        }
        User myUser = this.userRepository.findByUsername(username);
        String myUserPassword = myUser == null ? "nullPass" : myUser.getPassword();
        System.out.println("username: " + username + " rawPassword: " + rawPassword + "myUser.getPassword(): " + myUserPassword);
        if (myUser == null)
            throw new UsernameNotFoundException(username);
        else if (!passwordEncoder.matches(rawPassword, myUser.getPassword())){
            System.out.println("myUser.password :" + myUser.getPassword());
            System.out.println("rawPassword :" + rawPassword);
            throw new PasswordNotCorrectException(username);
        }else{
            Map<String, Object> response = new HashMap<>();
            //JSONObject.
            System.out.println(myUser.toString());
            response.put("token", tokenUtil.generateToken(myUser));
            response.put("userDetails", myUser.toString());
            return response;
            //return "{\"token\":\"" + tokenUtil.generateToken(myUser) + "\"}";
        }
    }

    public String setUpConference(ConferenceRequest request){

        Conference newConference=new Conference(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())),
                request.getConferenceAbbreviation(),request.getConferenceFullName(),request.getConferenceTime(),
                request.getConferenceLocation(),request.getContributeEndTime(),request.getResultReleaseTime()
                );
        User user=this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        user.getConferencesId().add(newConference.getConferenceId());
        conferenceRepository.save(newConference);
        System.out.println(newConference.toString());
        //默认成功
        return "{\"message\":\"conference application submit success\"}";
    }



}
