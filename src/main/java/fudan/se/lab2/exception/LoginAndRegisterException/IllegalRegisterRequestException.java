package fudan.se.lab2.exception.LoginAndRegisterException;

public class IllegalRegisterRequestException extends RuntimeException  {
    public IllegalRegisterRequestException(String username){
        super("Illegal register request for username '" + username + "'");
    }
}
