package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;

/**
 * @author hyf
 * 这个类获取某会议具体信息
 */

public class UserGetConferenceDetailsRequest {

    private Long conferenceId;

    public UserGetConferenceDetailsRequest(){}
    public UserGetConferenceDetailsRequest(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public Long getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public String toString() {
        return "UserGetConferenceDetailsRequest{" +
                "conferenceId=" + conferenceId +
                '}';
    }
}


