package fudan.se.lab2.exception.ConferencException;

public class PaperSubmitOrModifyFailException extends RuntimeException {
    public PaperSubmitOrModifyFailException(String message) {
        super(message);
    }
}