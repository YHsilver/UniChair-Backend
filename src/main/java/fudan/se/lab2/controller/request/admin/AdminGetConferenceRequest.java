package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于管理员获取会议列表
 */

public class AdminGetConferenceRequest {

    private String name = "LOOK";

    private Conference.Status content;

    // empty constructor
    public AdminGetConferenceRequest() {
    }

    // constructor
    public AdminGetConferenceRequest(Conference.Status content) {
        this.content = content;
    }

    public Conference.Status getRequestContent() {
        return content;
    }

    @Override
    public String toString() {
        return "AdminGetConferenceRequest{" +
                "name=" + name +
                ", content=" + content +
                '}';
    }
}


