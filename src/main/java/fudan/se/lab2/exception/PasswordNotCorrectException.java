package fudan.se.lab2.exception;

public class PasswordNotCorrectException extends RuntimeException{
    public PasswordNotCorrectException(String username) {
        super("Username '" + username + "' password not correct");
    }
}
