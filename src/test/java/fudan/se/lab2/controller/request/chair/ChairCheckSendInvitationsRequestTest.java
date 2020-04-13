package fudan.se.lab2.controller.request.chair;

import fudan.se.lab2.domain.Invitation;
import fudan.se.lab2.exception.IllegalOperateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChairCheckSendInvitationsRequestTest {

    ChairCheckSendInvitationsRequest chairCheckSendInvitationsRequest = new ChairCheckSendInvitationsRequest("token",
            Invitation.Status.PENDING);

    @Test
    void getToken() {
        assertEquals("token", chairCheckSendInvitationsRequest.getToken());
        assertNotEquals("AToken", chairCheckSendInvitationsRequest.getToken());
    }

    @Test
    void getName() {
        assertEquals("CHECKSENDINVITATION", chairCheckSendInvitationsRequest.getName());
        assertNotEquals("LOOK", chairCheckSendInvitationsRequest.getName());
    }

    @Test
    void getStatus() {
        assertEquals(Invitation.Status.PENDING, chairCheckSendInvitationsRequest.getStatus());
        assertNotEquals(Invitation.Status.PASS, chairCheckSendInvitationsRequest.getStatus());
    }

    @Test
    void setToken() {
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            chairCheckSendInvitationsRequest.setToken("AToken");
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void setName() {
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            chairCheckSendInvitationsRequest.setName("LOOK");
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void setStatus() {
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            chairCheckSendInvitationsRequest.setStatus(Invitation.Status.PASS);
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void testToString() {
        assertEquals("ChairCheckSendInvitationsRequest{name='CHECKSENDINVITATION', token='token', status=PENDING}", chairCheckSendInvitationsRequest.toString());
    }
}