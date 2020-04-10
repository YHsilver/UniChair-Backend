package fudan.se.lab2.controller.request.user;


import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于 chair 改变会议状态
 */

public class ChairChangeConferenceStageRequest {

    // 3个属性
    private String name = "CHANGESTAGE";

    private Long conferenceId;

    private Conference.Stage changedStage;

    // empty constructor
    public ChairChangeConferenceStageRequest() {

    }

    // constructor
    public ChairChangeConferenceStageRequest(Conference.Stage changedStage, Long conferenceId) {
        this.changedStage = changedStage;
        this.conferenceId = conferenceId;
    }

    public String getName() {
        return name;
    }

    public Conference.Stage getChangedStage() {
        return changedStage;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setChangedStage(Conference.Stage changedStage) {
        this.changedStage = changedStage;
    }

    @Override
    public String toString() {
        return "ChairChangeConferenceStageRequest{" +
                "name=" + name +
                ", changedStage=" + changedStage +
                '}';
    }
}
