package fudan.se.lab2.controller;

public class CheckUsernameRegisteredOrNotRequest {
    private String username;

    public CheckUsernameRegisteredOrNotRequest() {
    }

    public CheckUsernameRegisteredOrNotRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "CheckUsernameRegisteredOrNotRequest{" +
                "username='" + username + '\'' +
                '}';
    }
}
