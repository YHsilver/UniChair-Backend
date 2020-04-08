package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.controller.request.AdminRequest;
import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于修改会议状态
 */

public class ChangeConferenceStatusRequest extends AdminRequest {

    private AdminRequest.Name requestName = AdminRequest.Name.CHANGESTATUS;

    private Long conferenceId;

    private Conference.Status changedStatus;

    // empty constructor
    public ChangeConferenceStatusRequest() {
        super();
    }

    // empty constructor
    public ChangeConferenceStatusRequest(Long conferenceId, Conference.Status status) {
        this.conferenceId = conferenceId;
        this.changedStatus = status;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public Conference.Status getChangedStatus() {
        return changedStatus;
    }

    @Override
    public String toString() {
        return "ChangeConferenceStatusRequest{" +
                "requestName=" + requestName +
                ", conferenceId=" + conferenceId +
                ", changedStatus=" + changedStatus +
                '}';
    }
}
