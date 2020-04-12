package fudan.se.lab2.exception;

public class IllegalOperateException extends RuntimeException {
    public IllegalOperateException() {
        super("Invalid operation!");
    }
}

