package fudan.se.lab2.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author hyf
 * 这个类用来处理邀请函
 */

@Entity
public class Invitation implements Serializable {

    // 6个参数
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationId;

    // 会议id
    private Long conferenceId;

    // 邀请人
    @OneToOne
    private User chair;

    // 被邀请人
    @OneToOne
    private User reviewer;

    // 邀请信息
    private String message;

    // 状态
    public enum Status {PENDING, PASS, REJECT}

    private Status status;

    public Invitation() {

    }

    public Invitation(Long invitationId, Long conferenceId, User chair, User reviewer, String message, Status status) {
        this.chair = chair;
        this.reviewer = reviewer;
        this.invitationId = invitationId;
        this.conferenceId = conferenceId;
        this.message = message;
        this.status = status;

    }

    public Status getStatus() {
        return status;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public String getMessage() {
        return message;
    }

    public User getChair() {
        return chair;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setChair(User chair) {
        this.chair = chair;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationId=" + invitationId +
                ", conferenceId=" + conferenceId +
                ", chair=" + chair +
                ", reviewer=" + reviewer +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
