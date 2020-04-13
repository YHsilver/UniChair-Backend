package fudan.se.lab2.controller.request.chair;

import fudan.se.lab2.exception.IllegalOperateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChairInviteReviewersRequestTest {

    private ChairInviteReviewersRequest chairInviteReviewersRequest = new ChairInviteReviewersRequest("username",
            2L, "message", "reviewer", "token",
            "conferenceFullName");

    @Test
    void getConferenceFullName() {
        assertEquals("conferenceFullName", chairInviteReviewersRequest.getConferenceFullName());
        assertNotEquals("conference", chairInviteReviewersRequest.getConferenceFullName());
    }

    @Test
    void getToken() {
        assertEquals("token", chairInviteReviewersRequest.getToken());
        assertNotEquals("Atoken", chairInviteReviewersRequest.getToken());
    }

    @Test
    void getReviewer() {
        assertEquals("reviewer", chairInviteReviewersRequest.getReviewer());
        assertNotEquals("conference", chairInviteReviewersRequest.getReviewer());
    }

    @Test
    void getMessage() {
        assertEquals("message", chairInviteReviewersRequest.getMessage());
        assertNotEquals("conference", chairInviteReviewersRequest.getMessage());
    }

    @Test
    void getConferenceId() {
        assertEquals(2L, chairInviteReviewersRequest.getConferenceId());
        assertNotEquals(0L, chairInviteReviewersRequest.getConferenceId());
    }

    @Test
    void getName() {
        assertEquals("INVITE", chairInviteReviewersRequest.getName());
        assertNotEquals("conference", chairInviteReviewersRequest.getName());
    }

    @Test
    void getUsername() {
        assertEquals("username", chairInviteReviewersRequest.getUsername());
        assertNotEquals("conference", chairInviteReviewersRequest.getUsername());
    }

    @Test
    void setName() {
        Throwable exception = assertThrows(IllegalOperateException.class, () -> {
            chairInviteReviewersRequest.setName("HELLo");
        });
        assertEquals("Invalid operation!", exception.getMessage());
    }

    @Test
    void setUsername() {
        chairInviteReviewersRequest.setUsername("admin");
        assertEquals("admin", chairInviteReviewersRequest.getUsername());
        assertNotEquals("nobody", chairInviteReviewersRequest.getUsername());
    }

    @Test
    void setConferenceFullName() {
        chairInviteReviewersRequest.setConferenceFullName("admin");
        assertEquals("admin", chairInviteReviewersRequest.getConferenceFullName());
        assertNotEquals("nobody", chairInviteReviewersRequest.getConferenceFullName());
    }

    @Test
    void setToken() {
        chairInviteReviewersRequest.setToken("admin");
        assertEquals("admin", chairInviteReviewersRequest.getToken());
        assertNotEquals("nobody", chairInviteReviewersRequest.getToken());
    }

    @Test
    void setMessage() {
        chairInviteReviewersRequest.setMessage("admin");
        assertEquals("admin", chairInviteReviewersRequest.getMessage());
        assertNotEquals("nobody", chairInviteReviewersRequest.getMessage());
    }

    @Test
    void setConferenceId() {
        chairInviteReviewersRequest.setConferenceId(3L);
        assertEquals(3L, chairInviteReviewersRequest.getConferenceId());
        assertNotEquals(2L, chairInviteReviewersRequest.getConferenceId());
    }

    @Test
    void setReviewer() {
        chairInviteReviewersRequest.setReviewer("admin");
        assertEquals("admin", chairInviteReviewersRequest.getReviewer());
        assertNotEquals("nobody", chairInviteReviewersRequest.getReviewer());
    }

    @Test
    void testToString() {
        assertEquals("ChairInviteReviewersRequest{name='INVITE', username='username', conferenceId=2, message='message', reviewer='reviewer', conferenceFullName='conferenceFullName'}", chairInviteReviewersRequest.toString());
        assertNotEquals("ChairInviteReviewersRequest{name=INVITE, username='username'}",
                chairInviteReviewersRequest.toString());
    }
}