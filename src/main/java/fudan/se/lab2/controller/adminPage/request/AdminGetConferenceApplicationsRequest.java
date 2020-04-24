package fudan.se.lab2.controller.adminPage.request;

import fudan.se.lab2.domain.conference.Conference;

/**
 * @author hyf(modified pxy)
 * 这个类用于管理员获取指定status或全部的会议列表
 */

public class AdminGetConferenceApplicationsRequest {

    private Conference.Status status;

    // constructor
    public AdminGetConferenceApplicationsRequest(Conference.Status status) {
        this.status = status;
    }

    public Conference.Status getStatus() {
        return status;
    }

    public void setContent(Conference.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AdminGetConferenceApplicationsRequest{" +
                "status=" + status +
                '}';
    }
}


