package fudan.se.lab2.service.registerPage;

import fudan.se.lab2.controller.registerPage.request.RegisterRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.exception.LoginAndRegisterException.UsernameHasBeenRegisteredException;
import fudan.se.lab2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        if (this.userRepository.findByUsername(username) != null) {
            throw new UsernameHasBeenRegisteredException(username);
        }
        User newUser = new User(username, this.passwordEncoder.encode(request.getPassword()), fullName, unit, area, email);
        this.userRepository.save(newUser);
        return newUser;
    }
}
