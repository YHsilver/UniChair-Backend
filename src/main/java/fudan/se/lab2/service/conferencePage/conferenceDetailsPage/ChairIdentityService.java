package fudan.se.lab2.service.conferencePage.conferenceDetailsPage;

import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairChangeConferenceStageRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairCheckInvitationsRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairSendInvitationRequest;
import fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity.ChairSearchReviewersRequest;
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
import java.util.Set;

@Service
public class ChairIdentityService {

    private UserRepository userRepository;
    private ConferenceRepository conferenceRepository;
    private InvitationRepository invitationRepository;
    private JwtTokenUtil tokenUtil;

    @Autowired
    public ChairIdentityService(UserRepository userRepository, InvitationRepository invitationRepository,
                        ConferenceRepository conferenceRepository, JwtTokenUtil tokenUtil) {
        this.userRepository = userRepository;
        this.conferenceRepository = conferenceRepository;
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
        User chair = this.userRepository.findByUsername(tokenUtil.getUsernameFromToken(request.getToken()));
        Conference.Stage changedStage = request.getChangedStage();
        Conference thisConference = conferenceRepository.findByConferenceId(request.getConferenceId());
        thisConference.setStage(changedStage);
        conferenceRepository.save(thisConference);
        thisUser.addConference(thisConference);
        return thisConference.getConferenceFullName() + "'s Stage is " + thisConference.getStage().toString();
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
            if (eachUser.getFullName().equals(fullName)) {
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
    public String inviteReviewers(ChairSendInvitationRequest request) {
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
     * @param request the ChairCheckInvitationsRequest request
     * @return return conferences' lists
     */
    public List<JSONObject> checkSendInvitations(ChairCheckInvitationsRequest request) {
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
