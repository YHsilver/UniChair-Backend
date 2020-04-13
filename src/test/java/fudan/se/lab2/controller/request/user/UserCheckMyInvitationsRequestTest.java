package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.exception.IllegalOperateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            userCheckMyInvitationsRequest.setToken("HELLo");
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void setName() {
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            userCheckMyInvitationsRequest.setName("HELLo");
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void setStatus() {
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            userCheckMyInvitationsRequest.setStatus(Invitation.Status.PASS);
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void testToString() {
        assertEquals("UserCheckMyInvitationsRequest{name='CHECKMYINVITATION'}", userCheckMyInvitationsRequest.toString());
    }
}