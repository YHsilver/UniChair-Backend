package fudan.se.lab2.exception.ConferencException;

/**
 * @author hyf
 * 用户的不合法操作
 */

public class IllegalConferenceOperateException extends RuntimeException {
    public IllegalConferenceOperateException() {
        super("Invalid conference operate!");
    }
}
