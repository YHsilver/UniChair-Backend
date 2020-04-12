package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.admin.AdminChangeConferenceStatusRequest;
import fudan.se.lab2.controller.request.admin.AdminGetConferenceRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static fudan.se.lab2.service.UserService.getConferenceJsonObjects;

/**
 * @author LBW
 * 这个类是具体响应类
 * “响应服务”
 */

@Service
public class AdminService {

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

    // empty constructor
    public AdminService() {

    }

    // constructor
    @Autowired
    public AdminService(UserRepository userRepository, AuthorityRepository authorityRepository,
                        ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    /**
     * check whether the AdminShowConference request can be successful(管理员查看会议)
     *
     * @param request the AdminRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> ShowConference(AdminGetConferenceRequest request) {
        Conference.Status status = request.getContent();
//        System.out.println(request);
//        System.out.println(getConferenceJsonObjects(status, this.conferenceRepository));
        return getConferenceJsonObjects(status, this.conferenceRepository);
    }

    /**
     * check whether the Admin changeConferenceStatus request can be successful(管理员修改会议状态)
     *
     * @param request the changeConferenceStatus request
     * @return return conference's ID & Status
     */
    public String changeConferenceStatus(AdminChangeConferenceStatusRequest request) {
        User thisChair = this.userRepository.findByUsername(request.getChair());
        Conference thisConference = this.conferenceRepository.findByConferenceId(request.getId());
        thisConference.setStatus(request.getStatus());
        this.conferenceRepository.save(thisConference);
        thisChair.getConferences().add(thisConference);
        return thisConference.getConferenceFullName() + "'s Status is " + thisConference.getStatus().toString() + " now!";
    }
}
