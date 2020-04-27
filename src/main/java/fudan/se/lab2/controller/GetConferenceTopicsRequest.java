package fudan.se.lab2.controller;

public class GetConferenceTopicsRequest {
    private Long conferenceId;
    public GetConferenceTopicsRequest(){}

    public GetConferenceTopicsRequest(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public String toString() {
        return "GetConferenceTopicsRequest{" +
                "conferenceId=" + conferenceId +
                '}';
    }
}
