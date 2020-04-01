package fudan.se.lab2.controller.request;

/**
 * @author LBW
 * 这个类用来处理登陆申请
 */
public class LoginRequest {

    // 2个参数
    // user name
    private String username;

    // password
    private String password;

    // empty constructor
    public LoginRequest() {
    }

    // constructor
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

//    public void setUsername(String username) {
//        this.username = username;
//    }

    public String getPassword() {
        return password;
    }

//    public void setPassword(String password) {
//        this.password = password;
//    }
}
