package fudan.se.lab2.domain;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
import java.io.Serializable;

import static fudan.se.lab2.domain.Conference.String2Json;

/**
 * @author hyf
 * 这个类用来处理邀请函
 */

@Entity
public class Invitation implements Serializable {

    // 7个参数
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationId;

    // 会议id
    private Long conferenceId;

    // 会议全名
    private String conferenceFullName;

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

    public Invitation(Long conferenceId, String conferenceFullName, User chair, User reviewer,
                      String message) {
        this.chair = chair;
        this.reviewer = reviewer;
        this.conferenceFullName = conferenceFullName;
        this.conferenceId = conferenceId;
        this.message = message;
        // 最初都是待接收
        this.status = Status.PENDING;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
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

    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
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

    public JSONObject toStandardJson() {
        try {
            String str = "{" +
                    "\"invitationId\":\"" + invitationId.toString() + '\"' +
                    ", \"conferenceId\":\"" + conferenceId.toString() + '\"' +
                    ", \"sender\":\"" + chair.getUsername().toString() + '\"' +
                    ", \"fullName\":\"" + chair.getFullName().toString() + '\"' +
                    ", \"conferenceFullName\":\"" + conferenceFullName.toString() + '\"' +
                    ", \"reviewer\":\"" + reviewer.toString() + '\"' +
                    ", \"message\":\"" + message.toString() + '\"' +
                    ", \"status\":\"" + status.toString() + '\"' +
                    '}';
            return String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
