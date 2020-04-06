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

    public AuthService() {

    }

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

    /**
     * check whether the register request can be successful
     *
     * @param request the register request
     * @return return new User if success, one is "token" and the other is "userDetails"
     * @throws UsernameHasBeenRegisteredException if username has been registered
     */
    public User register(RegisterRequest request) {
        String username = request.getUsername();
        String fullName = request.getFullname();
        String unit = request.getUnit();
        String area = request.getArea();
        String email = request.getEmail();
        if (userRepository.findByUsername(username) != null) {
            throw new UsernameHasBeenRegisteredException(username);
        }
        User newUser = new User(username, passwordEncoder.encode(request.getPassword()), fullName, unit, area, email,
                new HashSet<>());
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

    /**
     * check whether the ConferenceRequest request can be successful
     *
     * @param request the ConferenceRequest request
     * @return return a successful message if success
     */
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

    /**
     * check whether the ConferenceManagement request can be successful
     *
     * @param request the ConferenceManagement request
     * @return return conference's state if request is "changeStatus"
     */
    // 管理员处理会议
    public String ConferenceManagement(ConferenceManagementRequest request) {
        String name = request.getName();
        switch (name) {
            case "lookUp": {
                System.out.println(this.conferenceRepository.findAll());
                return this.conferenceRepository.findAll().toString();
            }
            case "changeStatus": {
                Conference conference = this.conferenceRepository.findByConferenceId(Long.parseLong(request.getContent()[0]));
                conference.setStatus(Conference.Status.valueOf(request.getContent()[1]));
                return conference.getConferenceId() + conference.getStatus();
            }
            default: {
                return null;
            }
        }
    }

}
