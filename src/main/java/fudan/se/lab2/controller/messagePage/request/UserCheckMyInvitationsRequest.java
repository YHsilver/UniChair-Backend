package fudan.se.lab2.controller.messagePage.request;


import fudan.se.lab2.domain.conference.Invitation;

/**
 * @author hyf
 * 这个类用于 user 查看自己收到的邀请
 */

public class UserCheckMyInvitationsRequest {

    private String token;
    private Invitation.Status status;

    public UserCheckMyInvitationsRequest(){}

    public UserCheckMyInvitationsRequest(String token, Invitation.Status status) {
        this.token = token;
        this.status = status;
    }

    public Invitation.Status getStatus() {
        return status;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) { this.token = token; }
    public void setStatus(Invitation.Status status) { this.status = status; }

    @Override
    public String toString() {
        return "UserCheckMyInvitationsRequest{" +
                "token='" + token + '\'' +
                ", status=" + status +
                '}';
    }
}
