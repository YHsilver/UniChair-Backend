package fudan.se.lab2.controller.request;

/**
 * @author hyf
 * 这个来用于分发 Users 的请求
 */

public class UserRequest {

    private String token;

    // 查看会议、投稿、邀请 PC members……
    public static enum Name {LOOK, SUBMIT, INVITE}

    private Name requestName;

    // empty constructor
    public UserRequest() {
    }

    // constructor
    public UserRequest(String token, Name name) {
        this.token = token;
        this.requestName = name;
    }

    public String getToken() {
        return token;
    }

    public Name getRequestName() {
        return requestName;
    }
}
