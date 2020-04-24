package fudan.se.lab2.service;

import fudan.se.lab2.service.GeneralService.GetUserDetailsService;
import org.junit.jupiter.api.Test;

/**
 * @author hyf
 * this is a test class for AuthService
 */

class GetUserDetailsServiceTest {

    private GetUserDetailsService getUserDetailsService = new GetUserDetailsService();

    @Test
    void testRegister() {
//        // success
//
//        Authority userAuthority = new Authority("userAuthority");
//        // a fake register request
//        RegisterRequest registerRequest = new RegisterRequest("username", "password", "fullName", "unit", "area",
//                "email");
//        authService.register(registerRequest);
//        User newUser = new User(registerRequest.getUsername(), registerRequest.getPassword(),
//                registerRequest.getFullName(), registerRequest.getUnit(), registerRequest.getArea(),
//                registerRequest.getEmail(), new HashSet<>(Collections.singletonList(userAuthority)));
//        assertEquals(newUser, authService.register(registerRequest));
//
//        // exception
    }

    @Test
    void testLogin() {
    }

}