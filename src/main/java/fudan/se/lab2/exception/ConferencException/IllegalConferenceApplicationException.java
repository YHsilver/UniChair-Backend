package fudan.se.lab2.exception.ConferencException;

public class IllegalConferenceApplicationException extends RuntimeException {
    public IllegalConferenceApplicationException() {
        super("Invalid conference application");
    }
}
