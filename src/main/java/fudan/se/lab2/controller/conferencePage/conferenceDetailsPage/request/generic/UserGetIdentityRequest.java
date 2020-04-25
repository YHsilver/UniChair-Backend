package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

public class UserGetIdentityRequest {

    private String token;
    private Long conferenceId;

    public UserGetIdentityRequest(){}

    public UserGetIdentityRequest(String token, Long conferenceId){
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
        return "UserGetIdentityRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                '}';
    }
}
