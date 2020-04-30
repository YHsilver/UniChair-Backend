package fudan.se.lab2.controller.applicationPage.request;

import fudan.se.lab2.domain.conference.Conference;

/**
 * @author hyf
 * 这个类用于获取自己申请的会议列表（通过、被拒绝、待审核）
 */

public class UserGetConferenceApplicationsRequest {

    // token
    private String token;
    private Conference.Status status;

    public UserGetConferenceApplicationsRequest(){}

    public UserGetConferenceApplicationsRequest(String token, Conference.Status status){
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Conference.Status getStatus() {
        return status;
    }

    public void setStatus(Conference.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserGetConferenceApplicationsRequest{" +
                "token='" + token + '\'' +
                ", status=" + status +
                '}';
    }
}


