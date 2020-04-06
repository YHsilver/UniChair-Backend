package fudan.se.lab2.controller.request;

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
        return fullName;
    }

//    public void setFullname(String fullName) {
//        this.fullName = fullName;
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

//    public Set<Authority> getAuthorities() {
//        return authorities;
//    }

//     public void setAuthorities(Set<String> authorities) {
//         this.authorities = authorities;
//     }

    @Override
    public String toString() {
        return "\nusername: " + this.getUsername()
                + "\npassword: " + this.getPassword()
                + "\nfullName: " + this.getFullname()
                + "\nunit: " + this.getUnit()
                + "\narea: " + this.getArea()
                + "\nemail: " + this.getEmail();
    }
}

