package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.exception.ConferencException.IllegalConferenceOperateException;

/**
 * @author hyf
 * 这个类用于获取通过的全部会议列表，以投稿
 */

public class UserGetAllConferenceRequest {

    // 2个属性
    private String name = "LOOK";

    private Conference.Status requestContent = Conference.Status.PASS;

    // empty constructor
    public UserGetAllConferenceRequest() {
    }

    // constructor
    public UserGetAllConferenceRequest(Conference.Status requestContent) {
        this.requestContent = requestContent;
    }

    public String getName() {
        return name;
    }

    public Conference.Status getRequestContent() {
        return requestContent;
    }

    public void setName(String name) {
        System.out.println(name);
        throw new IllegalConferenceOperateException();
    }

    public void setRequestContent(Conference.Status requestContent) {
        System.out.println(requestContent);
        throw new IllegalConferenceOperateException();
    }

    @Override
    public String toString() {
        return "UserGetAllConferenceRequest{" +
                "name=" + name +
                ", requestContent=" + requestContent +
                '}';
    }
}


