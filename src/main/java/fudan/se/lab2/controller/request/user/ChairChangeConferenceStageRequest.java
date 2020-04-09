package fudan.se.lab2.controller.request.user;


import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于 chair 改变会议状态
 */

public class ChairChangeConferenceStageRequest   {

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

    public Conference.Stage getChangedStage() {
        return changedStage;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    @Override
    public String toString() {
        return "ChairChangeConferenceStageRequest{" +
                "name=" + name +
                ", changedStage=" + changedStage +
                '}';
    }
}
