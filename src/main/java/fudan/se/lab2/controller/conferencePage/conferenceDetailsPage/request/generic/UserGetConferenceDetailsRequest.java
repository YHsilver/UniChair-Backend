package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

/**
 * @author hyf
 * 这个类获取m某会议具体信息
 */

public class UserGetConferenceDetailsRequest {

    private Long conferenceId;

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


