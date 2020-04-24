package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.conferencePage.conferenceAbstractPage.request.UserGetPassedConferenceRequest;
import fudan.se.lab2.domain.conference.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author hyf
 * this is a test class for UserGetPassedConferenceRequest
 */

class UserGetPassedConferenceRequestTest {

    private UserGetPassedConferenceRequest userGetPassedConferenceRequest = new UserGetPassedConferenceRequest();

    @Test
    void testGetName() {
        assertEquals("LOOK", userGetPassedConferenceRequest.getName());
        assertNotEquals("BIUBIU", userGetPassedConferenceRequest.getName());
    }

    @Test
    void testGetRequestContent() {
        assertEquals(Conference.Status.PASS, userGetPassedConferenceRequest.getRequestContent());
        assertNotEquals(Conference.Status.REJECT, userGetPassedConferenceRequest.getRequestContent());
        assertNotEquals(Conference.Status.PENDING, userGetPassedConferenceRequest.getRequestContent());
    }

    @Test
    void testSetName() {
        userGetPassedConferenceRequest.setName("BIUBIU");
        assertEquals("BIUBIU", userGetPassedConferenceRequest.getName());
    }

    @Test
    void testSetRequestContent() {
        userGetPassedConferenceRequest.setRequestContent(Conference.Status.PASS);
        assertEquals(Conference.Status.PASS, userGetPassedConferenceRequest.getRequestContent());
    }

    @Test
    void testToString() {
        assertNotEquals("", userGetPassedConferenceRequest.toString());
        assertEquals("UserGetPassedConferenceRequest{name=LOOK, requestContent=PASS}", userGetPassedConferenceRequest.toString());
    }
}