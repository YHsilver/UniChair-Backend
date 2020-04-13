package fudan.se.lab2.controller.request.chair;

import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.exception.IllegalOperateException;

/**
 * @author hyf
 * 这个类用于 chair 查看自己发出的邀请函
 */

public class ChairCheckSendInvitationsRequest {

    private String name = "CHECKSENDINVITATION";

    private String token;

    private Invitation.Status status;

    public ChairCheckSendInvitationsRequest() {

    }

    public ChairCheckSendInvitationsRequest(String token, Invitation.Status status) {
        this.status = status;
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
        throw new IllegalOperateException();
    }

    public void setName(String name) {
        throw new IllegalOperateException();
    }

    public void setStatus(Invitation.Status status) {
        throw new IllegalOperateException();
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

