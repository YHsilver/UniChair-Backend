package fudan.se.lab2.controller.request.chair;

/**
 * @author hyf
 * 这个类用于邀请 PC members
 */

public class ChairInviteReviewersRequest {

    // 2个属性
    private String name = "INVITE";

    private String username;

    // empty constructor
    public ChairInviteReviewersRequest() {
    }

    // constructor
    public ChairInviteReviewersRequest(String username) {
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
        return "ChairInviteReviewersRequest{" +
                "name=" + name +
                ", username='" + username + '\'' +
                '}';
    }
}

