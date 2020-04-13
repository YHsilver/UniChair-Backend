package fudan.se.lab2.controller.request.admin;

import fudan.se.lab2.domain.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hyf
 * this is a test class for AdminChangeConferenceStatusRequest
 */

class AdminChangeConferenceStatusRequestTest {

    private AdminChangeConferenceStatusRequest adminChangeConferenceStatusRequest =
            new AdminChangeConferenceStatusRequest(2L, Conference.Status.PENDING);

    @Test
    void testGetName() {
        assertEquals("CHANGESTATUS", adminChangeConferenceStatusRequest.getName());
        assertNotEquals("LOOK", adminChangeConferenceStatusRequest.getName());
    }

    @Test
    void testGetId() {
        assertEquals(2L, adminChangeConferenceStatusRequest.getId());
        assertNotEquals(0L, adminChangeConferenceStatusRequest.getId());
    }

    @Test
    void testGetStatus() {
        assertEquals(Conference.Status.PENDING, adminChangeConferenceStatusRequest.getStatus());
        assertNotEquals(Conference.Status.PASS, adminChangeConferenceStatusRequest.getStatus());
        assertNotEquals(Conference.Status.REJECT, adminChangeConferenceStatusRequest.getStatus());
    }

    @Test
    void testSetName() {
        adminChangeConferenceStatusRequest.setName("BIUBIU");
        assertNotEquals("CHANGESTATUS", adminChangeConferenceStatusRequest.getName());
        assertEquals("BIUBIU", adminChangeConferenceStatusRequest.getName());
    }

    @Test
    void testSetId() {
        adminChangeConferenceStatusRequest.setId(0L);
        assertNotEquals(2L, adminChangeConferenceStatusRequest.getId());
        assertEquals(0L, adminChangeConferenceStatusRequest.getId());
    }

    @Test
    void testSetStatus() {
        adminChangeConferenceStatusRequest.setStatus(Conference.Status.PASS);
        assertNotEquals(Conference.Status.PENDING, adminChangeConferenceStatusRequest.getStatus());
        assertEquals(Conference.Status.PASS, adminChangeConferenceStatusRequest.getStatus());
    }

    @Test
    void testToString() {
        assertNotEquals("AdminChangeConferenceStatusRequest{name=CHANGESTATUS, id=2L, status=PENDING}",
                adminChangeConferenceStatusRequest.toString());
        assertEquals("AdminChangeConferenceStatusRequest{name=CHANGESTATUS, id=2, status=PENDING, chair=null}",
                adminChangeConferenceStatusRequest.toString());
    }
}