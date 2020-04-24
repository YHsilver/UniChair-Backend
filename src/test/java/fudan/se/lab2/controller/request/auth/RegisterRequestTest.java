package fudan.se.lab2.controller.request.auth;

import fudan.se.lab2.controller.registerPage.request.RegisterRequest;
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
        assertEquals("David John", registerRequest.getFullName());
        assertNotEquals("Davy", registerRequest.getFullName());
    }

    @Test
    void testGetUnit() {
        assertEquals("Software", registerRequest.getUnit());
        assertNotEquals("Computer", registerRequest.getUnit());
    }

    @Test
    void testGetArea() {
        assertEquals("FUDAN", registerRequest.getArea());
        assertNotEquals("JIAODA", registerRequest.getArea());
    }

    @Test
    void testGetEmail() {
        assertEquals("David@fudan.edu.cn", registerRequest.getEmail());
        assertNotEquals("Davy@fudan.edu.cn", registerRequest.getEmail());
    }
    // @Test
    // void testGetAuthorities() {
    // }

    @Test
    void testSetUsername() {
        registerRequest.setUsername("Bob");
        assertEquals("Bob", registerRequest.getUsername());
        assertNotEquals("Davy", registerRequest.getUsername());
    }

    @Test
    void testSetPassword() {
        registerRequest.setPassword("3355997");
        assertEquals("3355997", registerRequest.getPassword());
        assertNotEquals("123456", registerRequest.getPassword());
    }

    @Test
    void testSetFullName() {
        registerRequest.setFullName("Bob Steve");
        assertEquals("Bob Steve", registerRequest.getFullName());
        assertNotEquals("Bob Leo", registerRequest.getFullName());
    }

    @Test
    void testSetUnit() {
        registerRequest.setUnit("International");
        assertEquals("International", registerRequest.getUnit());
        assertNotEquals("Software", registerRequest.getUnit());
    }

    @Test
    void testSetArea() {
        registerRequest.setArea("Shanghai");
        assertEquals("Shanghai", registerRequest.getArea());
        assertNotEquals("Beijing", registerRequest.getArea());
    }

    @Test
    void testSetEmail() {
        registerRequest.setEmail("example@fudan.com");
        assertEquals("example@fudan.com", registerRequest.getEmail());
        assertNotEquals("David@fudan.com", registerRequest.getEmail());
    }

//    @Test
//    void testSetAuthorities() {
//        assertEquals("Software", registerRequest.getUnit());
//        assertNotEquals("Computer", registerRequest.getUnit());
//    }

    @Test
    void testToString() {
        assertEquals("RegisterRequest{username='David', password='123456', fullName='David John', unit='Software', area='FUDAN', email='David@fudan.edu.cn'}", registerRequest.toString());
        assertNotEquals("Computer", registerRequest.toString());
    }
}