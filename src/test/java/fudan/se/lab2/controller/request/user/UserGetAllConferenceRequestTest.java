package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        userGetAllConferenceRequest.setName("BIUBIU");
        assertEquals("BIUBIU", userGetAllConferenceRequest.getName());
    }

    @Test
    void testSetRequestContent() {
        userGetAllConferenceRequest.setRequestContent(Conference.Status.PASS);
        assertEquals(Conference.Status.PASS, userGetAllConferenceRequest.getRequestContent());
    }

    @Test
    void testToString() {
        assertNotEquals("", userGetAllConferenceRequest.toString());
        assertEquals("UserGetAllConferenceRequest{name=LOOK, requestContent=PASS}", userGetAllConferenceRequest.toString());
    }
}