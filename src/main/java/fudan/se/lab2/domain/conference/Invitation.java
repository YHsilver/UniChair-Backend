package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import javax.persistence.*;
import java.io.Serializable;

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

    @ManyToOne
    private Conference conference;
    @ManyToOne
    private User chair;
    @ManyToOne
    private User reviewer;

    private String message;
    public enum Status {PENDING, PASS, REJECT}
    private Status status;

    public Invitation() {}

    public Invitation(Conference conference, User chair, User reviewer,
                      String message) {
        this.chair = chair;
        this.conference = conference;
        this.reviewer = reviewer;
        this.message = message;
        this.status = Status.PENDING;
    }

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public User getChair() {
        return chair;
    }

    public void setChair(User chair) {
        this.chair = chair;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "invitationId=" + invitationId +
                ", conference=" + conference +
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
                    ", \"conferenceId\":\"" + conference.getConferenceId() + '\"' +
                    ", \"sender\":\"" + chair.getUsername() + '\"' +
                    ", \"fullName\":\"" + chair.getFullName() + '\"' +
                    ", \"conferenceFullName\":\"" + conference.getConferenceFullName() + '\"' +
                    ", \"reviewerUsername\":\"" + reviewer.getUsername() + '\"' +
                    ", \"reviewerFullName\":\"" + reviewer.getFullName() + '\"' +
                    ", \"reviewerEmail\":\"" + reviewer.getEmail() + '\"' +
                    ", \"message\":\"" + message + '\"' +
                    ", \"status\":\"" + status + '\"' +
                    '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
