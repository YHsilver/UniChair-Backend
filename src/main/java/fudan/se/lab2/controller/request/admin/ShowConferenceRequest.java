package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.controller.request.AdminRequest;
import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于获取会议列表
 */

public class ShowConferenceRequest extends AdminRequest {

    private AdminRequest.Name name = AdminRequest.Name.LOOK;

    private Conference.Status requestContent;

    // empty constructor
    public ShowConferenceRequest() {
    }

    // constructor
    public ShowConferenceRequest(Conference.Status requestContent) {
        this.requestContent = requestContent;
    }

    public Conference.Status getRequestContent() {
        return requestContent;
    }
}


