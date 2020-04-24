package fudan.se.lab2.service;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetConferenceDetailsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserGetIdentityRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic.UserSubmitPaperRequest;
import fudan.se.lab2.controller.messagePage.request.UserCheckMyInvitationsRequest;
import fudan.se.lab2.controller.messagePage.request.UserDecideInvitationsRequest;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.repository.*;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author LBW、hyf
 * 这个类是响应 user controller
 */

@Service
public class UserService {

    // 用户仓库
    private UserRepository userRepository;

    // 会议仓库
    private ConferenceRepository conferenceRepository;

    // 稿件仓库
    private PaperRepository paperRepository;

    // 邀请函仓库
    private InvitationRepository invitationRepository;

    // 加密密码
    private PasswordEncoder passwordEncoder;

    // token
    private JwtTokenUtil tokenUtil;

    // constructor
    @Autowired
    public UserService(UserRepository userRepository, InvitationRepository invitationRepository,
                       ConferenceRepository conferenceRepository, PaperRepository paperRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.paperRepository = paperRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
    }




//    /**
//     * 得到会议的JSON格式，辅助 getMyConference 方法
//     *
//     * @param status      查找的会议状态
//     * @param conferences user的会议库（个人）
//     * @return Conferences JSONObject list
//     */
//    public static List<JSONObject> getConferenceJsonObjects(Conference.Status status, Set<Conference> conferences) {
//        List<JSONObject> list = Lists.newArrayList();
//        for (Conference eachConference : conferences) {
//            if (eachConference.getStatus().equals(status))
//                list.add(eachConference.toBriefJson());
//        }
//        return list;
//    }
//
//
//    /**
//     * 得到会议的JSON格式，辅助 getAllConference 方法
//     * public static!!!
//     *
//     * @param status               查找的会议状态
//     * @param conferenceRepository 查找的会议仓库（全局查找）
//     * @return Conferences JSONObject list
//     */
//    public static List<JSONObject> getConferenceJsonObjects(Conference.Status status, ConferenceRepository conferenceRepository) {
//        Iterable<Conference> conferences = conferenceRepository.findAll();
//        List<JSONObject> list = Lists.newArrayList();
//        conferences.forEach(eachConference -> {
//            if (eachConference.getStatus() == status)
//                list.add(eachConference.toBriefJson());
//        });
//        return list;
//    }









//    /**
//     * 得到会议的JSON格式，辅助 checkMyInvitations 方法
//     * public static!!!
//     *
//     * @param status      查找的会议状态
//     * @param Invitations user的会议库（个人）
//     * @return Invitations JSONObject list
//     */
//    public static List<JSONObject> getInvitationJsonObjects(Invitation.Status status, Set<Invitation> Invitations) {
//        List<JSONObject> list = Lists.newArrayList();
//        for (Invitation eachInvitation : Invitations) {
//            System.out.println("all"+eachInvitation.toStandardJson());
//            if (eachInvitation.getStatus() == status){
//                list.add(eachInvitation.toStandardJson());
//                System.out.println("hit"+eachInvitation.toStandardJson());
//            }
//        }
//        return list;
//    }



}
