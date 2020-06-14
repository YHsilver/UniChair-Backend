package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

public class UserGetPassedPapersRequest {
    private String token;
    private Long conferenceId;


    public UserGetPassedPapersRequest() {
    }

    public UserGetPassedPapersRequest(String token, Long conferenceId) {
        this.token = token;
        this.conferenceId = conferenceId;
    }

    public String getToken() {
        return token;
    }

    public Long getConferenceId() {
        return conferenceId;
    }
}
