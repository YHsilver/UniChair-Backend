package fudan.se.lab2.controller;

/**
 * @author hyf
 * <p>
 * 前端发token，后端发送user details
 */

public class GetUserDetailsRequest {

    private String token;

    public GetUserDetailsRequest(){}
    public GetUserDetailsRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "GetUserDetailsRequest{" +
                "token='" + token + '\'' +
                '}';
    }
}
