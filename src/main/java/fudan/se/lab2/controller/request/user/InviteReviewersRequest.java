package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.request.UserRequest;

/**
 * @author hyf
 * 这个类用于邀请PC members
 */

public class InviteReviewersRequest extends UserRequest {

    private Name name = Name.INVITE;

    private String username;

    // empty constructor
    public InviteReviewersRequest() {
        super();

    }

    // constructor
    public InviteReviewersRequest(String username) {
        this.username = username;
    }

    public Name getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "InvitePCMembersRequest{" +
                "name=" + name +
                ", username='" + username + '\'' +
                '}';
    }
}

