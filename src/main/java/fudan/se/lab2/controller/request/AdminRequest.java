package fudan.se.lab2.controller.request;

/**
 * @author hyf
 * 这个来用于分发 Admin 的请求
 */
public class AdminRequest {

    public static enum Name {LOOK, CHANGESTATUS}

    private Name requestName;

    // empty constructor
    public AdminRequest() {
    }

    // constructor
    public AdminRequest(Name requestName) {
        this.requestName = requestName;
    }

    public Name getRequestName() {
        return requestName;
    }
}
