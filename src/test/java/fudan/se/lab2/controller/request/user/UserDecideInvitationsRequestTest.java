package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.domain.Invitation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDecideInvitationsRequestTest {

    UserDecideInvitationsRequest request
            = new UserDecideInvitationsRequest("token", 2L, Invitation.Status.PENDING);

    @Test
    void setStatus() {
        request.setStatus(Invitation.Status.PASS);
        assertEquals(Invitation.Status.PASS, request.getStatus());
        assertNotEquals(Invitation.Status.REJECT, request.getStatus());
    }

    @Test
    void setToken() {
        request.setToken("tt");
        assertEquals("tt", request.getToken());
        assertNotEquals("token", request.getToken());
    }

    @Test
    void setName() {
        request.setName("Name~");
        assertEquals("Name~", request.getName());
        assertNotEquals("Name", request.getName());
    }

    @Test
    void setInvitaionId() {
        request.setInvitaionId(3L);
        assertEquals(3L, request.getInvitaionId());
        assertNotEquals(2L, request.getInvitaionId());
    }

    @Test
    void testToString() {
        assertEquals("UserDecideInvitationsRequest{token='token', name='DECIDE', invitaionId=2, status=PENDING}", request.toString());
        assertNotEquals("", request.toString());
    }
}