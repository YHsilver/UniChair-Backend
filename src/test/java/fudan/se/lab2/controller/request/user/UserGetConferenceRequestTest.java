package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.exception.ConferencException.IllegalConferenceOperateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGetConferenceRequestTest {

    private UserGetConferenceRequest userGetConferenceRequest = new UserGetConferenceRequest();

    @Test
    void testGetName() {
        assertEquals("LOOK", userGetConferenceRequest.getName());
        assertNotEquals("BIUBIU", userGetConferenceRequest.getName());
    }

    @Test
    void testGetRequestContent() {
        assertEquals(Conference.Status.PASS, userGetConferenceRequest.getRequestContent());
        assertNotEquals(Conference.Status.REJECT, userGetConferenceRequest.getRequestContent());
        assertNotEquals(Conference.Status.PENDING, userGetConferenceRequest.getRequestContent());
    }

    @Test
    void testSetName() {
        Throwable exception = assertThrows(IllegalConferenceOperateException.class, () -> {
            userGetConferenceRequest.setName("BIUBIU");
        });
        assertEquals("Invalid conference operate!", exception.getMessage());
    }

    @Test
    void testSetRequestContent() {
        Throwable exception = assertThrows(IllegalConferenceOperateException.class, () -> {
            userGetConferenceRequest.setRequestContent(Conference.Status.PASS);
        });
        assertEquals("Invalid conference operate!", exception.getMessage());
    }

    @Test
    void testToString() {
        assertNotEquals("", userGetConferenceRequest.toString());
        assertEquals("UserGetConferenceRequest{name=LOOK, requestContent=PASS}", userGetConferenceRequest.toString());
    }
}