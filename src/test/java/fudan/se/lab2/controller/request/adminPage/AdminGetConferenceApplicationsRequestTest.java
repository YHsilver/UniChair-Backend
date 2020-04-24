package fudan.se.lab2.controller.request.adminPage;

import fudan.se.lab2.controller.adminPage.request.AdminGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.conference.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hyf
 * this is a test class for AdminGetConferenceApplicationsRequest
 */

class AdminGetConferenceApplicationsRequestTest {

    private AdminGetConferenceApplicationsRequest adminGetConferenceApplicationsRequest = new AdminGetConferenceApplicationsRequest("LOOK",
            Conference.Status.PENDING);

    @Test
    void testGetName() {
        assertEquals("LOOK", adminGetConferenceApplicationsRequest.getName());
        assertNotEquals("HELLO", adminGetConferenceApplicationsRequest.getName());
    }

    @Test
    void testGetContent() {
        assertEquals(Conference.Status.PENDING, adminGetConferenceApplicationsRequest.getContent());
        assertNotEquals(Conference.Status.REJECT, adminGetConferenceApplicationsRequest.getContent());
    }

    @Test
    void testSetName() {
        adminGetConferenceApplicationsRequest.setName("HELLO");
        assertNotEquals("LOOK", adminGetConferenceApplicationsRequest.getName());
        assertEquals("HELLO", adminGetConferenceApplicationsRequest.getName());
    }

    @Test
    void setContent() {
        adminGetConferenceApplicationsRequest.setContent(Conference.Status.PASS);
        assertNotEquals(Conference.Status.REJECT, adminGetConferenceApplicationsRequest.getContent());
        assertEquals(Conference.Status.PASS, adminGetConferenceApplicationsRequest.getContent());
    }

    @Test
    void testToString() {
        assertNotEquals("AdminGetConferenceApplicationsRequest{name=LOOK, content=PASS}", adminGetConferenceApplicationsRequest.toString());
        assertEquals("AdminGetConferenceApplicationsRequest{name=LOOK, content=PENDING}", adminGetConferenceApplicationsRequest.toString());
    }
}