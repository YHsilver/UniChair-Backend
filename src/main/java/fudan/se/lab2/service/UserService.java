package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.user.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.AuthorityRepository;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        User chairMan = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
//        System.out.println(chairMan);
        // 这里时间不对，应该有个“时差”的关系？？？
        Conference newConference = new Conference(chairMan,
                request.getConferenceAbbreviation(), request.getConferenceFullName(),
                request.getConferenceTime().plusDays(1L),
                request.getConferenceLocation(), request.getContributeEndTime().plusDays(1L),
                request.getResultReleaseTime().plusDays(1L)
        );
//        System.out.println(newConference);
        chairMan.getConferences().add(newConference);
        conferenceRepository.save(newConference);
        //默认成功
        return "{\"message\":\"conference application submit success!\"}";
    }

    /**
     * 得到会议的JSON格式
     *
     * @param status               查找的会议状态
     * @param conferenceRepository 查找的会议仓库（全局查找）
     * @return Conferences JSONObject list
     */

    public static List<JSONObject> getConferenceJsonObjects(Conference.Status status, ConferenceRepository conferenceRepository) {
        Iterable<Conference> conferences = conferenceRepository.findAll();
        List<JSONObject> list = Lists.newArrayList();
        conferences.forEach(eachConference -> {
            if (eachConference.getStatus() == status)
                list.add(eachConference.toStandardJson());
        });
        return list;
    }

    /**
     * check whether the UserShowConference request can be successful(用户查看全部通过的会议)
     *
     * @param request the UserGetConferenceRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getConference(UserGetConferenceRequest request) {
        Conference.Status status = request.getRequestContent();
        return getConferenceJsonObjects(status, this.conferenceRepository);
    }


    /**
     * 得到会议的JSON格式
     *
     * @param status      查找的会议状态
     * @param conferences user的会议库（个人）
     * @return Conferences JSONObject list
     */
    private List<JSONObject> getConferenceJsonObjects(Conference.Status status, Set<Conference> conferences) {
        List<JSONObject> list = Lists.newArrayList();
        for (Conference eachConference : conferences) {
            if (eachConference.getStatus() == status)
                list.add(eachConference.toStandardJson());
        }
        return list;
    }

    /**
     * check whether the UserShowConference request can be successful(用户查看自己申请的会议)
     *
     * @param request the UserGetMyConferenceRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getMyConference(UserGetMyConferenceRequest request) {
        Conference.Status status = request.getRequestContent();
        User thisUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        return getConferenceJsonObjects(status, thisUser.getConferences());
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

    /**
     * check my invitations(用户查看自己的邀请函)
     *
     * @param request the UserRequest request
     * @return return conferences' lists
     */
    public String checkMyInvitations(UserCheckMyInvitationsRequest request) {
        return "OK";
    }

    /**
     * check my invitations(用户查看自己发出的邀请函)
     *
     * @param request the UserRequest request
     * @return return conferences' lists
     */
    public String checkSendInvitations(UserCheckSendInvitationsRequest request) {
        return "OK";
    }
}
