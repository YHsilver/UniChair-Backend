package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.user.*;
import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.domain.Paper;
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
import java.util.Set;

/**
 * @author LBW、hyf
 * 这个类是响应 user controller
 */

@Service
public class UserService {

    // 用户仓库
    private UserRepository userRepository;

    // 权限仓库
    private AuthorityRepository authorityRepository;

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

    public UserService() {

    }

    // constructor
    @Autowired
    public UserService(UserRepository userRepository, AuthorityRepository authorityRepository, InvitationRepository invitationRepository,
                       ConferenceRepository conferenceRepository, PasswordEncoder passwordEncoder, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.conferenceRepository = conferenceRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
    }

    /**
     * check whether the ConferenceRequest request can be successful(用户申请会议)
     *
     * @param request the ConferenceRequest request
     * @return return a successful message if success
     */
    public String setUpConference(UserSetUpConferenceRequest request) {
        User chairMan = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        // System.out.println(chairMan);
        // 这里时间不对，应该有个“时差”的关系？？？
        Conference newConference = new Conference(chairMan,
                request.getConferenceAbbreviation(), request.getConferenceFullName(), request.getConferenceTime().plusDays(1L),
                request.getConferenceLocation(), request.getContributeStartTime().plusDays(1L), request.getContributeEndTime().plusDays(1L),
                request.getResultReleaseTime().plusDays(1L), request.getIntroduction());
//        System.out.println(newConference);
        chairMan.addConference(newConference);
        conferenceRepository.save(newConference);
        //默认成功
        return "{\"message\":\"conference application submit success!\"}";
    }

    /**
     * ???
     *
     * @param request ???
     * @return ???
     */
    public String getIdentity(UserGetIdentityRequest request) {
        String resp = "[";
        User currUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference currConference = this.conferenceRepository.findByConferenceId(request.getConferenceId());

        if (currUser.getId().equals(currConference.getChairMan().getId())) {
            resp += "0,";
        } else {
            resp += "1,";
        }

        if (currConference.getReviewerSet().contains(currUser)) {
            resp += "0,";
        } else {
            resp += "1,";
        }

        if (currConference.getAuthorSet().contains(currUser)) {
            resp += "0";
        } else {
            resp += "1";
        }

        return resp + "]";
    }

    /**
     * 得到会议的JSON格式，辅助 getMyConference 方法
     *
     * @param status      查找的会议状态
     * @param conferences user的会议库（个人）
     * @return Conferences JSONObject list
     */
    private List<JSONObject> getConferenceJsonObjects(Conference.Status status, Set<Conference> conferences) {
        List<JSONObject> list = Lists.newArrayList();
        for (Conference eachConference : conferences) {
            if (eachConference.getStatus().equals(status))
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
//        System.out.println(thisUser.getConferences());
        return getConferenceJsonObjects(status, thisUser.getConferences());
    }

    /**
     * 得到会议的JSON格式，辅助 getAllConference 方法
     * public static!!!
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
     * check whether the UserShowConference request can be successful(用户查看全部通过的会议，以投稿)
     *
     * @param request the UserGetAllConferenceRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> getAllConference(UserGetAllConferenceRequest request) {
        Conference.Status status = request.getRequestContent();
        return getConferenceJsonObjects(status, this.conferenceRepository);
    }

    /**
     * 获得详细信息
     *
     * @param request the UserGetConferenceDetailsRequest request
     * @return return conferences info
     */
    public JSONObject getConferenceDetails(UserGetConferenceDetailsRequest request) {
        Conference thisConference = this.conferenceRepository.findByConferenceId(request.getConferenceId());
        return thisConference.toFullJson();
    }


    /**
     * 删除 File，配合 submitPaper
     *
     * @param files multiFile
     */
    private void deleteFile(File... files) throws NoSuchFileException, DirectoryNotEmptyException, IOException {
        for (File file : files)
            if (file.exists()) {
                Files.delete(Paths.get(file.getPath()));
            }
    }

    /**
     * User submit paper（用户投稿）
     *
     * @param request the UserRequest request
     * @return return message
     */
    public String submitPaper(UserSubmitPaperRequest request) throws IOException {
        User Author = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        MultipartFile multipartFile = request.getFile();
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String prefix = fileName.substring(fileName.lastIndexOf('.'));
        File excelFile = File.createTempFile(String.valueOf(Math.random()), prefix);
        // MultipartFile to File
        multipartFile.transferTo(excelFile);
        Paper newPaper = new Paper(Author, request.getConferenceId(), request.getTitle(),
                request.getSummary(), excelFile);
        Author.getPapers().add(newPaper);
        paperRepository.save(newPaper);
        deleteFile(excelFile);
        // 成功
        return "{\"message\":\"your paper submit success!\"}";
    }

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

    /**
     * check my invitations(用户查看自己的邀请函)
     *
     * @param request the UserCheckMyInvitationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> checkMyInvitations(UserCheckMyInvitationsRequest request) {
        System.out.println("checkMyInvitations Called");
        User thisUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        System.out.println("full name" + thisUser.getFullName());
        System.out.println("this user's invi" + thisUser.getMyInvitations());
        List<JSONObject> list = Lists.newArrayList();
        for (Invitation eachInvitation : invitationRepository.findByReviewer(thisUser)) {
            System.out.println("all" + eachInvitation.toStandardJson());
            if (eachInvitation.getStatus() == request.getStatus()) {
                list.add(eachInvitation.toStandardJson());
                System.out.println("hit" + eachInvitation.toStandardJson());
            }
        }
        return list;
    }

    /**
     * decide my invitation（用户决定是否接受邀请）
     *
     * @param request the UserDecideInvitationsRequest request
     * @return successful message
     */
    public String decideInvitations(UserDecideInvitationsRequest request) {
        User thisUser = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Invitation thisInvitation = this.invitationRepository.findByInvitationId(request.getInvitationId());
        thisInvitation.setStatus(request.getStatus());
        Conference currConference = conferenceRepository.findByConferenceId(thisInvitation.getConferenceId());
        currConference.getReviewerSet().add(thisUser);
        this.conferenceRepository.save(currConference);

        this.invitationRepository.save(thisInvitation);

        return "Invitation " + thisInvitation.getInvitationId() + "'s Status is " + thisInvitation.getStatus().toString() + " now!";
    }

}
