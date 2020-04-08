package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.request.UserRequest;
import fudan.se.lab2.domain.Conference;

/**
 * @author hyf
 * 这个类用于获取会议列表
 */

public class ShowConferenceRequest extends UserRequest {

    private Name name = Name.LOOK;

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

    @Override
    public String toString() {
        return "ShowConferenceRequest{" +
                "name=" + name +
                ", requestContent=" + requestContent +
                '}';
    }
}


