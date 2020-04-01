package fudan.se.lab2.controller.request;

import java.util.Set;

/**
 * @author LBW
 * 这个类用来处理注册申请
 */

public class RegisterRequest {

    // 6个属性
    // user name
    private String username;

    // password
    private String password;

    // fullname
    private String fullname;

    // unit
    private String unit;

    // area
    private String area;

    // email
    private String email;

    // authorities
    private Set<String> authorities;

    // empty constructor
    public RegisterRequest() {
    }

    // constructor
    public RegisterRequest(String username, String password, String fullname, String unit, String area, String email, Set<String> authorities) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.unit = unit;
        this.area = area;
        this.email = email;
        this.authorities = authorities;
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

    public String getFullname() {
        return fullname;
    }

//    public void setFullname(String fullname) {
//        this.fullname = fullname;
//    }


    public String getUnit() {
        return unit;
    }

    public String getArea() {
        return area;
    }

    public String getEmail() {
        return email;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

//     public void setAuthorities(Set<String> authorities) {
//         this.authorities = authorities;
//     }
}

