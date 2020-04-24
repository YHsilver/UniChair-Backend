package fudan.se.lab2.service.indexPage;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.LoginAndRegisterException.PasswordNotCorrectException;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public LoginService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
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
        User thisUser = this.userRepository.findByUsername(username);

        if (thisUser == null) {
            throw new UsernameNotFoundException(username);
        } else if (!this.passwordEncoder.matches(rawPassword, thisUser.getPassword())) {
            System.out.println(thisUser.toString());
            throw new PasswordNotCorrectException(username);
        } else {
            System.out.println(thisUser.toString());
            Map<String, Object> response = new HashMap<>();
            response.put("token", this.tokenUtil.generateToken(thisUser));
            response.put("userDetails", thisUser.toStandardJson());
            return response;
        }
    }
}
