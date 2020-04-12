package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于管理员修改会议状态
 */

public class AdminChangeConferenceStatusRequest {

    // 4个属性
    private String name = "CHANGESTATUS";

    private Long id;

    private Conference.Status status;

    private String chair;

    // empty constructor
    public AdminChangeConferenceStatusRequest() {
        super();
    }

    // empty constructor
    public AdminChangeConferenceStatusRequest(Long id, Conference.Status status) {
        this.id = id;
        this.status = status;
    }

    public String getChair() {
        return chair;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Conference.Status getStatus() {
        return status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(Conference.Status status) {
        this.status = status;
    }

    public void setChair(String chair) {
        this.chair = chair;
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
