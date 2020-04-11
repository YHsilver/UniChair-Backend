package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.domain.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hyf
 * this is a test class for AdminGetConferenceRequest
 */

class AdminGetConferenceRequestTest {

    private AdminGetConferenceRequest adminGetConferenceRequest = new AdminGetConferenceRequest("LOOK",
            Conference.Status.PENDING);

    @Test
    void testGetName() {
        assertEquals("LOOK", adminGetConferenceRequest.getName());
        assertNotEquals("HELLO", adminGetConferenceRequest.getName());
    }

    @Test
    void testGetContent() {
        assertEquals(Conference.Status.PENDING, adminGetConferenceRequest.getContent());
        assertNotEquals(Conference.Status.REJECT, adminGetConferenceRequest.getContent());
    }

    @Test
    void testSetName() {
        adminGetConferenceRequest.setName("HELLO");
        assertNotEquals("LOOK", adminGetConferenceRequest.getName());
        assertEquals("HELLO", adminGetConferenceRequest.getName());
    }

    @Test
    void setContent() {
        adminGetConferenceRequest.setContent(Conference.Status.PASS);
        assertNotEquals(Conference.Status.REJECT, adminGetConferenceRequest.getContent());
        assertEquals(Conference.Status.PASS, adminGetConferenceRequest.getContent());
    }

    @Test
    void testToString() {
        assertNotEquals("AdminGetConferenceRequest{name=LOOK, content=PASS}", adminGetConferenceRequest.toString());
        assertEquals("AdminGetConferenceRequest{name=LOOK, content=PENDING}", adminGetConferenceRequest.toString());
    }
}