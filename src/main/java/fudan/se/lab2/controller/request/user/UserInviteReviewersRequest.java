package fudan.se.lab2.controller.request.user;

/**
 * @author hyf
 * 这个类用于邀请PC members
 */

public class UserInviteReviewersRequest {

    private String name = "INVITE";

    private String username;

    // empty constructor
    public UserInviteReviewersRequest() {
        super();

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

    @Override
    public String toString() {
        return "UserInviteReviewersRequest{" +
                "name=" + name +
                ", username='" + username + '\'' +
                '}';
    }
}

