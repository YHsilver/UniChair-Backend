package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity;

import java.util.Arrays;

/**
 * @author hyf
 * 这个类用于邀请 PC members
 */

public class ChairSendInvitationRequest {

    private String token;
    private Long conferenceId;
    private String[] reviewerUsername;

    public ChairSendInvitationRequest(String token, Long conferenceId, String[] reviewerUsername){
        this.token = token;
        this.conferenceId = conferenceId;
        this.reviewerUsername = reviewerUsername;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String[] getReviewerUsername() {
        return reviewerUsername;
    }

    public void setReviewerUsername(String[] reviewerUsername) {
        this.reviewerUsername = reviewerUsername;
    }

    @Override
    public String toString() {
        return "ChairSendInvitationRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", reviewerUsername=" + Arrays.toString(reviewerUsername) +
                '}';
    }
}

