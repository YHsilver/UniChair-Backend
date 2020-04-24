package fudan.se.lab2.controller.messagePage.request;


import fudan.se.lab2.domain.conference.Invitation;

/**
 * @author hyf
 * 这个类用于 user 查看自己被邀请的会议
 */

public class UserCheckMyInvitationsRequest {

    private String name = "CHECKMYINVITATION";

    private String token;

    private Invitation.Status status;

    public UserCheckMyInvitationsRequest() {

    }

    public UserCheckMyInvitationsRequest(String token, Invitation.Status status) {
        this.token = token;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public Invitation.Status getStatus() {
        return status;
    }

    public String getToken() {
        return token;
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
        return "UserCheckMyInvitationsRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
