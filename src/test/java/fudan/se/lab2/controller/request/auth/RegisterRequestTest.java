package fudan.se.lab2.controller.request.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hyf
 * this is a test class for RegisterRequest
 */

class RegisterRequestTest {

    private RegisterRequest registerRequest = new RegisterRequest("David", "123456", "David John", "Software", "FUDAN",
            "David@fudan.edu.cn");

    @Test
    void testGetUsername() {
        assertEquals("David", registerRequest.getUsername());
        assertNotEquals("Davy", registerRequest.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("123456", registerRequest.getPassword());
        assertNotEquals("12345", registerRequest.getPassword());
    }

    @Test
    void testGetFullName() {
    }

    @Test
    void testGetUnit() {
    }

    @Test
    void testGetArea() {
    }

    @Test
    void testGetEmail() {
    }

    @Test
    void testGetAuthorities() {
    }

    @Test
    void testSetUsername() {
    }

    @Test
    void testSetPassword() {
    }

    @Test
    void testSetFullName() {
    }

    @Test
    void testSetUnit() {
    }

    @Test
    void testSetArea() {
    }

    @Test
    void testSetEmail() {
    }

    @Test
    void testSetAuthorities() {
    }

    @Test
    void testToString() {
    }
}