package fudan.se.lab2.exception.LoginAndRegisterException;

/**
 * @author LBW
 */
public class UsernameHasBeenRegisteredException extends RuntimeException {
    private static final long serialVersionUID = -6074753940710869977L;

    public UsernameHasBeenRegisteredException(String username) {
        super("Username '" + username + "' has been registered");
    }
}
