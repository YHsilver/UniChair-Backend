package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Invitation;

/**
 * @author hyf
 * 这个类是用户决定邀请函的请求
 */

public class UserDecideInvitationsRequest {

    // 4个属性
    private String token;

    private String name = "DECIDE";

    private Long invitationId;

    private Invitation.Status status;

    public UserDecideInvitationsRequest() {

    }

    public UserDecideInvitationsRequest(String token, Long invitationId, Invitation.Status status) {
        this.invitationId = invitationId;
        this.status = status;
        this.token = token;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public Invitation.Status getStatus() {
        return status;
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    public void setStatus(Invitation.Status status) {
        this.status = status;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    @Override
    public String toString() {
        return "UserDecideInvitationsRequest{" +
                "token='" + token + '\'' +
                ", name='" + name + '\'' +
                ", invitationId=" + invitationId +
                ", status=" + status +
                '}';
    }
}
