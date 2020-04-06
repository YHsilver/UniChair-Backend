package fudan.se.lab2.service;

import fudan.se.lab2.controller.request.RegisterRequest;
import fudan.se.lab2.domain.Authority;
import fudan.se.lab2.domain.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author hyf
 * this is a test class for AuthService
 */
class AuthServiceTest {

    private AuthService authService = new AuthService();

    @Test
    void testRegister() {
        // success

        Authority userAuthority = new Authority("userAuthority");
        // a fake register request
        RegisterRequest registerRequest = new RegisterRequest("username", "password", "fullName", "unit", "area",
                "email");
        authService.register(registerRequest);
        User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword(),
                registerRequest.getFullname(), registerRequest.getUnit(), registerRequest.getArea(),
                registerRequest.getEmail(), new HashSet<>(Collections.singletonList(userAuthority)));
        assertEquals(newUser, authService.register(registerRequest));

        // exception
    }

    @Test
    void testLogin() {
    }

    @Test
    void testSetUpConference() {
    }

    @Test
    void testConferenceManagement() {
    }
}