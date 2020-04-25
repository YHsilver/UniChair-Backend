package fudan.se.lab2.controller.adminPage.request;

import fudan.se.lab2.domain.conference.Conference;

/**
 * @author hyf
 * 这个类用于管理员修改会议状态
 */

public class AdminChangeConferenceStatusRequest {

    private Long id;
    private Conference.Status status;

    public AdminChangeConferenceStatusRequest(){}

    public AdminChangeConferenceStatusRequest(Long id, Conference.Status status) {
        this.id = id;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public Conference.Status getStatus() {
        return status;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setStatus(Conference.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminChangeConferenceStatusRequest{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
