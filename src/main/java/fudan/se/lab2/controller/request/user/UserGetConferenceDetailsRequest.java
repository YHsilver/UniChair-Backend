package fudan.se.lab2.controller.request.user;

/**
 * @author hyf
 * 这个类获取m某会议具体信息
 */

public class UserGetConferenceDetailsRequest {

    private String name = "GETDETAILS";

    private Long conferenceId;

    public UserGetConferenceDetailsRequest() {

    }

    public UserGetConferenceDetailsRequest(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getName() {
        return name;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserGetConferenceDetailsRequest{" +
                "name='" + name + '\'' +
                ", conferenceId=" + conferenceId +
                '}';
    }
}


