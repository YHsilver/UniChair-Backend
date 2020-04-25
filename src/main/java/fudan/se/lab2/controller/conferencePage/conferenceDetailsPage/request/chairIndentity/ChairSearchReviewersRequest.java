package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity;

public class ChairSearchReviewersRequest {

    // get the chair
    private String token;
    // get the conferenceId to check the validation of chair
    // ignore Reviews who have accepted invitations before
    private Long conferenceId;
    // search target
    private String targetFullName;

    public ChairSearchReviewersRequest(String token, Long conferenceId, String targetFullName) {
        this.token = token;
        this.conferenceId = conferenceId;
        this.targetFullName = targetFullName;
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
    public String getTargetFullName() {
        return targetFullName;
    }
    public void setTargetFullName(String targetFullName) {
        this.targetFullName = targetFullName;
    }

    @Override
    public String toString() {
        return "ChairSearchReviewersRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", targetFullName='" + targetFullName + '\'' +
                '}';
    }
}
