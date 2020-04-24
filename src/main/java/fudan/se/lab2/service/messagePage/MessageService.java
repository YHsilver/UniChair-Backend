package fudan.se.lab2.service.messagePage;

import fudan.se.lab2.controller.messagePage.request.UserCheckMyInvitationsRequest;
import fudan.se.lab2.controller.messagePage.request.UserDecideInvitationsRequest;
import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Invitation;
import fudan.se.lab2.repository.ConferenceRepository;
import fudan.se.lab2.repository.InvitationRepository;
import fudan.se.lab2.repository.UserRepository;
import fudan.se.lab2.security.jwt.JwtTokenUtil;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private UserRepository userRepository;
    // 会议仓库
    private ConferenceRepository conferenceRepository;
    // 邀请函仓库
    private InvitationRepository invitationRepository;
    // token
    private JwtTokenUtil tokenUtil;

    // constructor
    @Autowired
    public MessageService(UserRepository userRepository, InvitationRepository invitationRepository,
                       ConferenceRepository conferenceRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
        this.tokenUtil = tokenUtil;
        this.invitationRepository = invitationRepository;
    }


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
