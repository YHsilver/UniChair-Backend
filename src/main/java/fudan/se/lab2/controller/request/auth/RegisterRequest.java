package fudan.se.lab2.controller.request.auth;

/**
 * @author LBW
 * 这个类用来处理注册申请
 */

public class RegisterRequest {

    // 7个属性
    // user name
    private String username;

    // password
    private String password;

    // fullName
    private String fullName;

    // unit
    private String unit;

    // area
    private String area;

    // email
    private String email;

    // authorities
//    private Set<Authority> authorities;

    // empty constructor
    public RegisterRequest() {
    }

    // constructor
    public RegisterRequest(String username, String password, String fullName, String unit, String area, String email) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.unit = unit;
        this.area = area;
        this.email = email;
//        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUnit() {
        return unit;
    }

    public String getArea() {
        return area;
    }

    public String getEmail() {
        return email;
    }

//    public Set<Authority> getAuthorities() {
//        return authorities;
//    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public void setAuthorities(Set<Authority> authorities) {
//        this.authorities = authorities;
//    }

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", fullName='" + fullName + '\'' +
                ", unit='" + unit + '\'' +
                ", area='" + area + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

