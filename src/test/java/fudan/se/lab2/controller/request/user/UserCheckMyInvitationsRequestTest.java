package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.messagePage.request.UserCheckMyInvitationsRequest;
import fudan.se.lab2.domain.conference.Invitation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author hyf
 * this is a test class for UserCheckMyInvitationsRequest
 */

class UserCheckMyInvitationsRequestTest {

    UserCheckMyInvitationsRequest userCheckMyInvitationsRequest = new UserCheckMyInvitationsRequest("token", Invitation.Status.PASS);

    @Test
    void getName() {
        assertEquals("CHECKMYINVITATION", userCheckMyInvitationsRequest.getName());
        assertNotEquals("BOO", userCheckMyInvitationsRequest.getName());
    }

    @Test
    void getStatus() {
        assertEquals(Invitation.Status.PASS, userCheckMyInvitationsRequest.getStatus());
        assertNotEquals(Invitation.Status.PENDING, userCheckMyInvitationsRequest.getStatus());
    }

    @Test
    void getToken() {
        assertEquals("token", userCheckMyInvitationsRequest.getToken());
        assertNotEquals("BOO", userCheckMyInvitationsRequest.getToken());
    }

    @Test
    void setToken() {
        userCheckMyInvitationsRequest.setToken("BIUBIU");
        assertEquals("BIUBIU", userCheckMyInvitationsRequest.getToken());
        assertNotEquals("HI", userCheckMyInvitationsRequest.getToken());
    }

    @Test
    void setName() {
        userCheckMyInvitationsRequest.setName("YEAH");
        assertEquals("YEAH", userCheckMyInvitationsRequest.getName());
        assertNotEquals("HH", userCheckMyInvitationsRequest.getName());
    }

    @Test
    void setStatus() {
        userCheckMyInvitationsRequest.setStatus(Invitation.Status.PASS);
        assertEquals(Invitation.Status.PASS, userCheckMyInvitationsRequest.getStatus());
        assertEquals(Invitation.Status.PASS, userCheckMyInvitationsRequest.getStatus());
    }

    @Test
    void testToString() {
        assertEquals("UserCheckMyInvitationsRequest{name='CHECKMYINVITATION'}", userCheckMyInvitationsRequest.toString());
    }
}