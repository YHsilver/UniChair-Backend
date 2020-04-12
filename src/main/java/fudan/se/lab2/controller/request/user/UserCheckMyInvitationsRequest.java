package fudan.se.lab2.controller.request.user;


import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.exception.IllegalOperateException;

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
        throw new IllegalOperateException();
    }

    public void setName(String name) {
        throw new IllegalOperateException();
    }

    public void setStatus(Invitation.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserCheckMyInvitationsRequest{" +
                "name='" + name + '\'' +
                '}';
    }
}
