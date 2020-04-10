package fudan.se.lab2.controller.request.admin;

/**
 * @author hyf
 * 这个类用于管理员获取会议列表
 */

public class AdminGetConferenceRequest {

    private String name;

    private String content;

    // empty constructor
    public AdminGetConferenceRequest() {
    }

    // constructor
    public AdminGetConferenceRequest(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public String getRequestContent() {
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


