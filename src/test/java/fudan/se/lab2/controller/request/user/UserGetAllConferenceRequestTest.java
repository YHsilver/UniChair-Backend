package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;
import fudan.se.lab2.exception.ConferencException.IllegalConferenceOperateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserGetAllConferenceRequestTest {

    private UserGetAllConferenceRequest userGetAllConferenceRequest = new UserGetAllConferenceRequest();

    @Test
    void testGetName() {
        assertEquals("LOOK", userGetAllConferenceRequest.getName());
        assertNotEquals("BIUBIU", userGetAllConferenceRequest.getName());
    }

    @Test
    void testGetRequestContent() {
        assertEquals(Conference.Status.PASS, userGetAllConferenceRequest.getRequestContent());
        assertNotEquals(Conference.Status.REJECT, userGetAllConferenceRequest.getRequestContent());
        assertNotEquals(Conference.Status.PENDING, userGetAllConferenceRequest.getRequestContent());
    }

    @Test
    void testSetName() {
        Throwable exception = assertThrows(IllegalConferenceOperateException.class, () -> {
            userGetAllConferenceRequest.setName("BIUBIU");
        });
        assertEquals("Invalid conference operate!", exception.getMessage());
    }

    @Test
    void testSetRequestContent() {
        Throwable exception = assertThrows(IllegalConferenceOperateException.class, () -> {
            userGetAllConferenceRequest.setRequestContent(Conference.Status.PASS);
        });
        assertEquals("Invalid conference operate!", exception.getMessage());
    }

    @Test
    void testToString() {
        assertNotEquals("", userGetAllConferenceRequest.toString());
        assertEquals("UserGetAllConferenceRequest{name=LOOK, requestContent=PASS}", userGetAllConferenceRequest.toString());
    }
}