package fudan.se.lab2.controller.request.chair;

import fudan.se.lab2.domain.User;

/**
 * @author hyf
 * 这个类用于邀请 PC members
 */

public class ChairInviteReviewersRequest {

    // 7个属性
    private String name = "INVITE";

    private String username;

    private Long conferenceId;

    private String message;

    private User reviewer;

    private String token;

    private String conferenceFullName;

    // empty constructor
    public ChairInviteReviewersRequest() {
    }

    // constructor
    public ChairInviteReviewersRequest(String username, Long conferenceId, String message, User reviewer, String token,
                                       String conferenceFullName) {
        this.username = username;
        this.conferenceFullName = conferenceFullName;
        this.conferenceId = conferenceId;
        this.token = token;
        this.message = message;
        this.reviewer = reviewer;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

    public String getToken() {
        return token;
    }

    public User getReviewer() {
        return reviewer;
    }

    public String getMessage() {
        return message;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    @Override
    public String toString() {
        return "ChairInviteReviewersRequest{" +
                "name=" + name +
                ", username='" + username + '\'' +
                '}';
    }
}
