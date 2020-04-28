package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity;

public class AuthorGetMyPapersRequest {
    private String token;
    private Long conferenceId;

    public AuthorGetMyPapersRequest() {}

    public AuthorGetMyPapersRequest(String token, Long conferenceId) {
        this.token = token;
        this.conferenceId = conferenceId;
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

    @Override
    public String toString() {
        return "AuthorGetMyPapersRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                '}';
    }
}
