package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity;

import fudan.se.lab2.domain.conference.Invitation;

/**
 * @author hyf
 * 这个类用于 chair 查看自己发出的邀请函
 */

public class ChairCheckInvitationsRequest {

    private String token;
    private Long conferenceId;
    private Invitation.Status status;

    public ChairCheckInvitationsRequest(String token, Long conferenceId, Invitation.Status status) {
        this.token = token;
        this.conferenceId = conferenceId;
        this.status = status;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
    public Invitation.Status getStatus() {
        return status;
    }
    public void setStatus(Invitation.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChairCheckInvitationsRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", status=" + status +
                '}';
    }
}

