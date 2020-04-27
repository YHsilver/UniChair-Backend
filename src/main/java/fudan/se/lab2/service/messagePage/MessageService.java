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
import fudan.se.lab2.service.UtilityService;
import org.assertj.core.util.Lists;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private JwtTokenUtil tokenUtil;
    
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
    public List<JSONObject> userCheckMyInvitations(UserCheckMyInvitationsRequest request) {
        User user = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        List<JSONObject> list = Lists.newArrayList();
        for (Invitation eachInvitation : invitationRepository.findByReviewer(user)) {
            if (eachInvitation.getStatus() == request.getStatus()) {
                list.add(eachInvitation.toStandardJson());
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
    public String userDecideInvitations(UserDecideInvitationsRequest request) {
        User user = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Invitation invitation = this.invitationRepository.findByInvitationId(request.getInvitationId());
        // only PENDING to PASS or REJECT
        if(invitation.getReviewer().getId().equals(user.getId())
                && request.getStatus() != Invitation.Status.PENDING
                && invitation.getStatus() == Invitation.Status.PENDING){

        }else{
            //TODO: throw exception
            return "Invalid request";
        }
        Conference currConference = conferenceRepository.findByConferenceId(invitation.getConferenceId());
        invitation.setStatus(request.getStatus());
        String[] selectedTopics = request.getTopics();
        if(request.getStatus() == Invitation.Status.PASS){
            // if accept the invitation, the user should select topics.
            if(selectedTopics == null || selectedTopics.length == 0
                    || !UtilityService.isTopicsValidInConference(currConference, selectedTopics)){
                return "Invalid topics selected";
            }
            for (String selectedTopic: selectedTopics) {
                currConference.findTopic(selectedTopic).getReviewers().add(user);
            }
        }

        currConference.getReviewerSet().add(user);
        this.conferenceRepository.save(currConference);
        this.invitationRepository.save(invitation);
        return "Invitation " + invitation.getInvitationId() + "'s Status is " + invitation.getStatus().toString() + " now!";
    }
}
