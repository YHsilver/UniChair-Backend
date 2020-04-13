package fudan.se.lab2.controller.request.chair;

import fudan.se.lab2.domain.Invitation;

/**
 * @author hyf
 * 这个类用于 chair 查看自己发出的邀请函
 */

public class ChairCheckSendInvitationsRequest {

    private String name = "CHECKSENDINVITATION";

    private String token;

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    private Long conferenceId;

    private Invitation.Status status;

    public ChairCheckSendInvitationsRequest() {

    }

    public ChairCheckSendInvitationsRequest(String token, Long conferenceId, Invitation.Status status) {
        this.status = status;
        this.conferenceId = conferenceId;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public Invitation.Status getStatus() {
        return status;
    }

    public void setToken(String token) {
//        throw new IllegalOperateException();
        this.token = token;
    }

    public void setName(String name) {
//        throw new IllegalOperateException();
        this.name = name;
    }

    public void setStatus(Invitation.Status status) {
//        throw new IllegalOperateException();
        this.status = status;
    }

    @Override
    public String toString() {
        return "ChairCheckSendInvitationsRequest{" +
                "name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", status=" + status +
                '}';
    }
}

