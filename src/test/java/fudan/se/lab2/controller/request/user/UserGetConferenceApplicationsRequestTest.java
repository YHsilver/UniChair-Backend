package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.applicationPage.request.UserGetConferenceApplicationsRequest;
import fudan.se.lab2.domain.conference.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author hyf
 * this is a test class for UserGetConferenceApplicationsRequest
 */

class UserGetConferenceApplicationsRequestTest {

    UserGetConferenceApplicationsRequest request = new UserGetConferenceApplicationsRequest(Conference.Status.PASS);

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