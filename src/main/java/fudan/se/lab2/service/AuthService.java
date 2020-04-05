package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.ConferenceManagementRequest;
import fudan.se.lab2.controller.request.ConferenceRequest;
import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.LoginAndRegisterException.PasswordNotCorrectException;
import fudan.se.lab2.exception.LoginAndRegisterException.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author LBW
 * 这个类是具体响应类
 * “响应服务”
 */
@Service

public class AuthService {

    // 用户仓库
    private UserRepository userRepository;

    // 权限仓库
    private AuthorityRepository authorityRepository;

    // 会议仓库
    private ConferenceRepository conferenceRepository;

    // 加密密码
    private PasswordEncoder passwordEncoder;

    // token
    private JwtTokenUtil tokenUtil;

    // constructor
    @Autowired
    public AuthService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    // 回应register的响应
    public User register(RegisterRequest request) {
        // TODO: Implement the function.
        String username = request.getUsername();
        User existUser = userRepository.findByUsername(username);
        if (existUser != null) {
            throw new UsernameHasBeenRegisteredException(username);
        }
        User newUser = new User(username, passwordEncoder.encode(request.getPassword()),
                request.getFullname(), new HashSet<>());
        // 'newUser' is better than 'user'

        userRepository.save(newUser);
        return newUser;
    }

    /**
     * check whether the login request can be successful
     *
     * @param username    the username in the login request
     * @param rawPassword the raw password in the login request
     * @return return a map with two entries if success, one is "token" and the other is "userDetails"
     * @throws UsernameNotFoundException   if username doesn't exist in the user repository
     * @throws PasswordNotCorrectException if password is not correct
     */
    public Map<String, Object> login(String username, String rawPassword) {
        if (username == null || rawPassword == null) {
            throw new UsernameNotFoundException(username == null ? "" : username);
        }
        User currentUser = userRepository.findByUsername(username);
        if (currentUser == null)
            throw new UsernameNotFoundException(username);
        else if (!passwordEncoder.matches(rawPassword, currentUser.getPassword())) {
            throw new PasswordNotCorrectException(username);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("token", tokenUtil.generateToken(currentUser));
            response.put("userDetails", currentUser.toJsonObject());
            return response;
        }
    }

    // 设置会议
    public String setUpConference(ConferenceRequest request) {
        Conference newConference = new Conference(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())),
                request.getConferenceAbbreviation(), request.getConferenceFullName(), request.getConferenceTime(),
                request.getConferenceLocation(), request.getContributeEndTime(), request.getResultReleaseTime()
        );
        User user = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        user.getConferencesId().add(newConference.getConferenceId());
        conferenceRepository.save(newConference);
        System.out.println(newConference.toString());

        //默认成功
        return "{\"message\":\"conference application submit success\"}";
    }

    // 管理员处理会议
    public String ConferenceManagement(ConferenceManagementRequest request) {
        String name = request.getName();
        switch (name) {
            case "LOOK": {
                System.out.println(this.conferenceRepository.findAll());
                return this.conferenceRepository.findAll().toString();
            }
            case "CHANGESTATE": {
                Conference conference = this.conferenceRepository.findByConferenceId(Long.parseLong(request.getContent()[0]));
                conference.setStage(Conference.Stage.valueOf(request.getContent()[1]));
                return null;
            }
            default: {
                return null;
            }
        }
    }

}
