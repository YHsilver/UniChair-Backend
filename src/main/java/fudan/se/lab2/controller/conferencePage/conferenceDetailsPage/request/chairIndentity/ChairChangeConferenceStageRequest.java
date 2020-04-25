package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity;


import fudan.se.lab2.domain.conference.Conference;

/**
 * @author hyf
 * 这个类用于 chair 改变会议状态
 */

public class ChairChangeConferenceStageRequest {

    private String token;
    private Long conferenceId;
    private Conference.Stage changedStage;

    public ChairChangeConferenceStageRequest(){}

    public ChairChangeConferenceStageRequest(String token, Conference.Stage changedStage, Long conferenceId) {
        this.token = token;
        this.changedStage = changedStage;
        this.conferenceId = conferenceId;
    }

    public String getToken() {
        return token;
    }
    public Conference.Stage getChangedStage() {
        return changedStage;
    }
    public Long getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
    public void setChangedStage(Conference.Stage changedStage) {
        this.changedStage = changedStage;
    }
    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "ChairChangeConferenceStageRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", changedStage=" + changedStage +
                '}';
    }
}
