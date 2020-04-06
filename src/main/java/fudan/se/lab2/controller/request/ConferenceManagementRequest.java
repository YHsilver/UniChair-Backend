package fudan.se.lab2.controller.request;

/**
 * @author hyf
 * 这个类管理员用来管理会议
 */

public class ConferenceManagementRequest {

    private enum Name {LOOK, CHANGESTATUS}

    private Name name;

    private String[] content;

    // empty constructor
    ConferenceManagementRequest() {

    }

    ConferenceManagementRequest(Name name, String[] content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name.toString();
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String[] getContent() {
        return this.content;
    }

    public void setContent(String[] content) {
        this.content = content;
    }
}
