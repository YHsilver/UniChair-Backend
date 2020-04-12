package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于获取自己的会议列表（通过、被拒绝、待审核）
 */

public class UserGetMyConferenceRequest {

    // token
    private String token;

    // 2个属性
    private String name = "LOOK";

    private Conference.Status requestContent;

    // empty constructor
    public UserGetMyConferenceRequest() {
    }

    // constructor
    public UserGetMyConferenceRequest(Conference.Status requestContent) {
        this.requestContent = requestContent;
    }

    public String getName() {
        return name;
    }

    public Conference.Status getRequestContent() {
        return requestContent;
    }

    public String getToken() {
        return token;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequestContent(Conference.Status requestContent) {
        this.requestContent = requestContent;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserGetMyConferenceRequest{" +
                "name=" + name +
                ", requestContent=" + requestContent +
                '}';
    }
}


