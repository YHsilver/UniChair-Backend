package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于获取会议列表
 */

public class UserGetConferenceRequest {

    // 2个属性
    private String name = "LOOK";

    private Conference.Status requestContent;

    // empty constructor
    public UserGetConferenceRequest() {
    }

    // constructor
    public UserGetConferenceRequest(Conference.Status requestContent) {
        this.requestContent = requestContent;
    }

    public String getName() {
        return name;
    }

    public Conference.Status getRequestContent() {
        return requestContent;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequestContent(Conference.Status requestContent) {
        this.requestContent = requestContent;
    }

    @Override
    public String toString() {
        return "UserGetConferenceRequest{" +
                "name=" + name +
                ", requestContent=" + requestContent +
                '}';
    }
}


