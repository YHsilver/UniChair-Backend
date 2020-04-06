package fudan.se.lab2.controller.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hyf
 * this is a test class for LoginRequest
 */
class LoginRequestTest {

    private String username1 = "admin";
    private String username2 = "Admin";
    private String password1 = "123456";
    private String password2 = "12345";

    // 生成的对应的是 username1 和 password1
    private LoginRequest loginRequest = new LoginRequest(this.username1, this.password1);

    @Test
    public void getUsernameTest() {
        assertEquals(this.username1, loginRequest.getUsername());
        assertNotEquals(this.username2, loginRequest.getUsername());
    }

    @Test
    void getPasswordTest() {
        assertEquals(this.password1, loginRequest.getPassword());
        assertNotEquals(this.password2, loginRequest.getPassword());
    }
}