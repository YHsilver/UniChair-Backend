package fudan.se.lab2.controller.request.user;

public class UserGetIdentityRequest {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    // token
    private String token;

    // 2个属性
    private String name = "GETIDENTITY";

    private Long conferenceId;


}
