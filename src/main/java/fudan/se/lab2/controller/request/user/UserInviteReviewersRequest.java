package fudan.se.lab2.controller.request.user;

/**
 * @author hyf
 * 这个类用于邀请PC members
 */

public class UserInviteReviewersRequest {

    // 2个属性
    private String name = "INVITE";

    private String username;

    // empty constructor
    public UserInviteReviewersRequest() {
    }

    // constructor
    public UserInviteReviewersRequest(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserInviteReviewersRequest{" +
                "name=" + name +
                ", username='" + username + '\'' +
                '}';
    }
}

