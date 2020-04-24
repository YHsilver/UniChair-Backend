package fudan.se.lab2.service.GeneralService;

import fudan.se.lab2.controller.GetUserDetailsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author LBW
 * 这个类是具体响应类
 * “响应服务”
 */

@Service
public class GetUserDetailsService {

    // 用户仓库
    private UserRepository userRepository;

    // 加密密码
    private PasswordEncoder passwordEncoder;

    // token
    private JwtTokenUtil tokenUtil;

    public GetUserDetailsService() {

    }

    // constructor
    @Autowired
    public GetUserDetailsService(UserRepository userRepository, ConferenceRepository conferenceRepository,
                                 PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }



    /**
     * 前端获取用户信息
     *
     * @param request the GetUserDetailsRequest request
     * @return User Details
     */
    public JSONObject getUserDetails(GetUserDetailsRequest request) {
        User thisUser = this.userRepository.findByUsername(this.tokenUtil.getUsernameFromToken(request.getToken()));
        return thisUser.toStandardJson();
    }

}
