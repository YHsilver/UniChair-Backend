package fudan.se.lab2.exception;

/**
 * @author hyf
 * general
 */

public class IllegalOperateException extends RuntimeException {
    public IllegalOperateException() {
        super("Invalid operation!");
    }
}

