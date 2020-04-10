package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于管理员获取会议列表
 */

public class AdminGetConferenceRequest {

    // 两个属性
    private String name;

    private Conference.Status content;

    // empty constructor
    public AdminGetConferenceRequest() {
    }

    // constructor
    public AdminGetConferenceRequest(String name, Conference.Status content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public Conference.Status getContent() {
        return content;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContent(Conference.Status content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AdminGetConferenceRequest{" +
                "name=" + name +
                ", content=" + content +
                '}';
    }
}


