package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于管理员修改会议状态
 */

public class AdminChangeConferenceStatusRequest   {

    private String name = "CHANGESTATUS";

    private Long id;

    private Conference.Status status;

    // empty constructor
    public AdminChangeConferenceStatusRequest() {
        super();
    }

    // empty constructor
    public AdminChangeConferenceStatusRequest(Long id, Conference.Status status) {
        this.id = id;
        this.status = status;
    }

    public Long getConferenceId() {
        return id;
    }

    public Conference.Status getChangedStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "AdminChangeConferenceStatusRequest{" +
                "name=" + name +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
