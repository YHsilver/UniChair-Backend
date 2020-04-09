package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.user.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author LBW
 * 这个类是具体响应类
 * “响应服务”
 */
@Service
public class UserService {

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

    public UserService() {

    }

    // constructor
    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository,
                       ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    /**
     * check whether the ConferenceRequest request can be successful(申请会议)
     *
     * @param request the ConferenceRequest request
     * @return return a successful message if success
     */
    public String setUpConference(UserSetUpConferenceRequest request) {
        System.out.println(request.toString());
        // 这里时间不对，应该有个“时差”的关系？？？
        Conference newConference = new Conference(userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken())),
                request.getConferenceAbbreviation(), request.getConferenceFullName(),
                request.getConferenceTime().plusDays(1L),
                request.getConferenceLocation(), request.getContributeEndTime().plusDays(1L),
                request.getResultReleaseTime().plusDays(1L)
        );
        User user = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        user.getConferencesId().add(newConference.getConferenceId());
        conferenceRepository.save(newConference);
//        System.out.println(newConference.toString());

        //默认成功
        return "{\"message\":\"conference application submit success!\"}";
    }

    /**
     * check whether the UserShowConference request can be successful(用户查看会议)
     *
     * @param request the UserRequest request
     * @return return conferences' lists
     */
    public String getConference(UserGetConferenceRequest request) {
        Conference.Status status = request.getRequestContent();
        // TODO
        System.out.println(this.conferenceRepository.findAll());
        return this.conferenceRepository.findAll().toString();
    }

    /**
     * changeConferenceStatus(chair 改变会议阶段)
     *
     * @param request the UserRequest request
     * @return return conference's id and changed stage
     */
    public String changeConferenceStatus(ChairChangeConferenceStageRequest request) {
        Conference.Stage changedStage = request.getChangedStage();
        Long conferenceId = request.getConferenceId();
        conferenceRepository.findByConferenceId(conferenceId).setStage(changedStage);
        return changedStage.toString() + conferenceId.toString();
    }

    /**
     * User submit paper
     *
     * @param request the UserRequest request
     * @return return message
     */
    public String submitPaper(UserSubmitPaperRequest request) {
        // TODO

        //默认成功
        return "{\"message\":\"your paper submit success!\"}";
    }

    /**
     * check whether the UserShowConference request can be successful(用户查看会议)
     *
     * @param request the UserRequest request
     * @return return conferences' lists
     */
    public String inviteReviewers(UserInviteReviewersRequest request) {
        // TODO
        //默认成功
        return "{\"message\":\"your invitation has been send!\"}";
    }
}
