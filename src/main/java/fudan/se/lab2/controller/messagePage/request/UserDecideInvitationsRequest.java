package fudan.se.lab2.controller.messagePage.request;

import fudan.se.lab2.domain.conference.Invitation;

import java.util.Arrays;

/**
 * @author hyf
 * 这个类是用户决定邀请函的请求
 */

public class UserDecideInvitationsRequest {

    // 4个属性
    private String token;
    private Long invitationId;
    private Invitation.Status status;
    private String[] topics;

    public UserDecideInvitationsRequest(){}

    public UserDecideInvitationsRequest(String token, Long invitationId, Invitation.Status status, String[] topics) {
        this.invitationId = invitationId;
        this.status = status;
        this.token = token;
        this.topics = topics;
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
    public void setStatus(Invitation.Status status) {
        this.status = status;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }
    public String[] getTopics() { return topics; }
    public void setTopics(String[] topics) { this.topics = topics; }

    @Override
    public String toString() {
        return "UserDecideInvitationsRequest{" +
                "token='" + token + '\'' +
                ", invitationId=" + invitationId +
                ", status=" + status +
                ", topics=" + Arrays.toString(topics) +
                '}';
    }
}
