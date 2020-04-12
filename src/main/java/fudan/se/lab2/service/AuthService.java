package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.auth.RegisterRequest;
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
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    /**
     * check whether the register request can be successful(注册)
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
     * check whether the login request can be successful(登录)
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
        if (currentUser == null) {
            throw new UsernameNotFoundException(username);
        } else if (!passwordEncoder.matches(rawPassword, currentUser.getPassword())) {
            throw new PasswordNotCorrectException(username);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("token", tokenUtil.generateToken(currentUser));
            response.put("userDetails", currentUser.toStandardJson());
            return response;
        }
    }

}
