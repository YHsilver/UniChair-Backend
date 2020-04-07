package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.admin.ChangeConferenceStatusRequest;
import fudan.se.lab2.controller.request.admin.ShowConferenceRequest;
import fudan.se.lab2.controller.request.initial.RegisterRequest;
import fudan.se.lab2.controller.request.user.SetUpConferenceRequest;
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
        String fullName = request.getFullName();
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
    public String setUpConference(SetUpConferenceRequest request) {
        // 这里时间不对，应该有个“时差”的关系？？？
        // TODO
        Conference newConference = new Conference(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())),
                request.getConferenceAbbreviation(), request.getConferenceFullName(),
                request.getConferenceTime().plusDays(1L),
                request.getConferenceLocation(), request.getContributeEndTime().plusDays(1L),
                request.getResultReleaseTime().plusDays(1L)
        );
        User user = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        user.getConferencesId().add(newConference.getConferenceId());
        conferenceRepository.save(newConference);
        System.out.println(newConference.toString());

        //默认成功
        return "{\"message\":\"conference application submit success\"}";
    }

    /**
     * check whether the AdminShowConference request can be successful
     *
     * @param request the AdminRequest request
     * @return return conferences' lists
     */
    // 管理员查看会议
    public String ShowConference(ShowConferenceRequest request) {
        Conference.Status status = request.getRequestContent();
        // TODO
        System.out.println(this.conferenceRepository.findAll());
        return this.conferenceRepository.findAll().toString();
    }

    /**
     * check whether the Admin changeConferenceStatus request can be successful
     *
     * @param request the changeConferenceStatus request
     * @return return conference's ID & Status
     */
    // 管理员修改会议状态
    public String changeConferenceStatus(ChangeConferenceStatusRequest request) {
        Conference thisConference = this.conferenceRepository.findByConferenceId(request.getConferenceId());
        thisConference.setStatus(request.getChangedStatus());
        return thisConference.getConferenceId() + thisConference.getStatus();
    }

}
