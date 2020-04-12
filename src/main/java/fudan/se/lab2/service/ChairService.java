package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.chair.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.request.chair.ChairCheckSendInvitationsRequest;
import fudan.se.lab2.controller.request.chair.ChairInviteReviewersRequest;
import fudan.se.lab2.controller.request.chair.ChairSearchReviewersRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author hyf
 * 这个类是响应 chair controller
 */

@Service
public class ChairService {

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

    public ChairService() {

    }

    // constructor
    @Autowired
    public ChairService(UserRepository userRepository, AuthorityRepository authorityRepository,
                        ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
    }

    /**
     * changeConferenceStatus(chair 改变会议阶段)
     *
     * @param request the UserRequest request
     * @return return conference's id and changed stage
     */
    public String changeConferenceStage(ChairChangeConferenceStageRequest request) {
        Conference.Stage changedStage = request.getChangedStage();
        Conference thisConference = conferenceRepository.findByConferenceId(request.getConferenceId());
        thisConference.setStage(changedStage);
        conferenceRepository.save(thisConference);
        return thisConference.getConferenceFullName() + "'s Stage is " + thisConference.getStage().toString() + " " +
                "now!";
    }

    /**
     * chair invite PC members
     *
     * @param request the ChairRequest request
     * @return return successful message
     */
    public String getReviewers(ChairSearchReviewersRequest request) {
        // TODO
        //默认成功
        return "{\"message\":\"your invitation has been send!\"}";
    }

    /**
     * chair invite PC members
     *
     * @param request the ChairRequest request
     * @return return successful message
     */
    public String inviteReviewers(ChairInviteReviewersRequest request) {
        // TODO
        //默认成功
        return "{\"message\":\"your invitation has been send!\"}";
    }

    /**
     * check send invitations(用户查看自己发出的邀请函)
     *
     * @param request the ChairRequest request
     * @return return conferences' lists
     */
    public String checkSendInvitations(ChairCheckSendInvitationsRequest request) {
        return "OK";
    }
}

