package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGetMyConferenceRequestTest {

    UserGetMyConferenceRequest request = new UserGetMyConferenceRequest(Conference.Status.PASS);

    @Test
    void setName() {
        request.setName("HAHA");
        assertEquals("HAHA", request.getName());
        assertNotEquals("BIUBIU", request.getName());
    }

    @Test
    void setRequestContent() {
        request.setRequestContent(Conference.Status.PENDING);
        assertEquals(Conference.Status.PENDING, request.getRequestContent());
        assertNotEquals(Conference.Status.REJECT, request.getRequestContent());
    }

    @Test
    void setToken() {
        request.setToken("Token");
        assertEquals("Token", request.getToken());
        assertNotEquals("BIUBIU", request.getToken());
    }

    @Test
    void testToString() {
        assertNotEquals("", request.toString());
    }
}