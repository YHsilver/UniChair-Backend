package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.chair.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.request.chair.ChairCheckSendInvitationsRequest;
import fudan.se.lab2.controller.request.chair.ChairInviteReviewersRequest;
import fudan.se.lab2.controller.request.chair.ChairSearchReviewersRequest;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;

//import static fudan.se.lab2.service.UserService.getInvitationJsonObjects;

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

    // 邀请函仓库
    private InvitationRepository invitationRepository;

    // 加密密码
    private PasswordEncoder passwordEncoder;

    // token
    private JwtTokenUtil tokenUtil;

    public ChairService() {

    }

    // constructor
    @Autowired
    public ChairService(UserRepository userRepository, AuthorityRepository authorityRepository, InvitationRepository invitationRepository,
                        ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
    }

    /**
     * changeConferenceStatus(chair 改变会议阶段)
     *
     * @param request the UserRequest request
     * @return return conference's id and changed stage
     */
    public String changeConferenceStage(ChairChangeConferenceStageRequest request) {
        User thisUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference.Stage changedStage = request.getChangedStage();
        Conference thisConference = conferenceRepository.findByConferenceId(request.getConferenceId());
        thisConference.setStage(changedStage);
        conferenceRepository.save(thisConference);
        thisUser.addConference(thisConference);
        return thisConference.getConferenceFullName() + "'s Stage is " + thisConference.getStage().toString() + " " +
                "now!";
    }

    /**
     * chair invite PC members
     *
     * @param request the ChairRequest request
     * @return return successful message
     */
    public List<JSONObject> getReviewers(ChairSearchReviewersRequest request) {
        String fullName = request.getFullName();
        Iterable<User> users = this.userRepository.findAll();
        List<JSONObject> list = Lists.newArrayList();

        users.forEach(eachUser -> {
            if (eachUser.getFullName().contains(fullName)) {
                list.add(eachUser.toStandardJson());
            }
        });

        return list;
    }

    /**
     * chair invite PC members
     *
     * @param request the ChairRequest request
     * @return return successful message
     */
    public String inviteReviewers(ChairInviteReviewersRequest request) {
        User chair = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        String[] targetNames = request.getReviewer();
        for (int i = 0; i < targetNames.length; i++) {
            User reviewer = this.userRepository.findByUsername(targetNames[i]);
            Invitation newInvitation = new Invitation(request.getConferenceId(), request.getConferenceFullName(), chair,
                    reviewer, request.getMessage());
            this.invitationRepository.save(newInvitation);
            chair.getSendInvitations().add(newInvitation);
            reviewer.getMyInvitations().add(newInvitation);
        }
        //默认成功
        return "{\"message\":\"your invitation has been send!\"}";
    }

    /**
     * check send invitations(用户查看自己发出的邀请函)
     *
     * @param request the ChairCheckSendInvitationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> checkSendInvitations(ChairCheckSendInvitationsRequest request) {
        User thisUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Long conferenceId = request.getConferenceId();
        Set<Invitation> invitationSet = invitationRepository.findByConferenceId(conferenceId);

        List<JSONObject> list = Lists.newArrayList();
        for (Invitation eachInvitation : invitationSet) {
            //if (eachInvitation.getStatus() == status)
            list.add(eachInvitation.toStandardJson());
        }
        return list;
    }
}

