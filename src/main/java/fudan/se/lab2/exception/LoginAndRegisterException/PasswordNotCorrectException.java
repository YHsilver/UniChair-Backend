package fudan.se.lab2.exception.LoginAndRegisterException;

public class PasswordNotCorrectException extends RuntimeException{
    public PasswordNotCorrectException(String username) {
        // TODO count the times of wrong password, when it increases to a certain value, suspend the account

        // return message is the same as UsernameNotFoundException because the client
        // should not know exactly the cause of failure
        super("Username '" + username + "' doesn't exist or password not correct");
    }
}
