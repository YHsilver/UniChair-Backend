package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ConferenceRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.exception.PasswordNotCorrectException;
import fudan.se.lab2.exception.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.controller.request.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.HashSet;

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

    public String login(String username, String rawPassword) {
        //tokenUtil.generateToken()
        User myUser = this.userRepository.findByUsername(username);
        if (myUser == null)
            throw new UsernameNotFoundException(username);
        else if (!passwordEncoder.matches(rawPassword, myUser.getPassword())){
            System.out.println("myUser.password :" + myUser.getPassword());
            System.out.println("rawPassword :" + rawPassword);
            throw new PasswordNotCorrectException(username);
        }else{
            return "{\"token\":\"" + tokenUtil.generateToken(myUser) + "\"}";
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
        //todo
        return "{\"message\":\"conference application submit success\"}";
    }



}
