//package fudan.se.lab2.controller.request.chair;
//
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
///**
// * @author hyf
// * this is a test class for ChairSendInvitationRequest
// */
//
//class ChairInviteReviewersRequestTest {
//
//    private ChairSendInvitationRequest chairInviteReviewersRequest = new ChairSendInvitationRequest("username",
//            2L, "message", "reviewer", "token",
//            "conferenceFullName");
//
//    @Test
//    void getConferenceFullName() {
//        assertEquals("conferenceFullName", chairInviteReviewersRequest.getConferenceFullName());
//        assertNotEquals("conference", chairInviteReviewersRequest.getConferenceFullName());
//    }
//
//    @Test
//    void getToken() {
//        assertEquals("token", chairInviteReviewersRequest.getToken());
//        assertNotEquals("Atoken", chairInviteReviewersRequest.getToken());
//    }
//
//    @Test
//    void getReviewer() {
//        assertEquals("reviewer", chairInviteReviewersRequest.getReviewer());
//        assertNotEquals("conference", chairInviteReviewersRequest.getReviewer());
//    }
//
//    @Test
//    void getMessage() {
//        assertEquals("message", chairInviteReviewersRequest.getMessage());
//        assertNotEquals("conference", chairInviteReviewersRequest.getMessage());
//    }
//
//    @Test
//    void getConferenceId() {
//        assertEquals(2L, chairInviteReviewersRequest.getConferenceId());
//        assertNotEquals(0L, chairInviteReviewersRequest.getConferenceId());
//    }
//
//    @Test
//    void getName() {
//        assertEquals("INVITE", chairInviteReviewersRequest.getName());
//        assertNotEquals("conference", chairInviteReviewersRequest.getName());
//    }
//
//    @Test
//    void getUsername() {
//        assertEquals("username", chairInviteReviewersRequest.getUsername());
//        assertNotEquals("conference", chairInviteReviewersRequest.getUsername());
//    }
//
//    @Test
//    void setName() {
//        chairInviteReviewersRequest.setName("HELLo");
//        assertEquals("HELLo", chairInviteReviewersRequest.getName());
//        assertNotEquals("HI", chairInviteReviewersRequest.getName());
//    }
//
//    @Test
//    void setUsername() {
//        chairInviteReviewersRequest.setUsername("adminPage");
//        assertEquals("adminPage", chairInviteReviewersRequest.getUsername());
//        assertNotEquals("nobody", chairInviteReviewersRequest.getUsername());
//    }
//
//    @Test
//    void setConferenceFullName() {
//        chairInviteReviewersRequest.setConferenceFullName("adminPage");
//        assertEquals("adminPage", chairInviteReviewersRequest.getConferenceFullName());
//        assertNotEquals("nobody", chairInviteReviewersRequest.getConferenceFullName());
//    }
//
//    @Test
//    void setToken() {
//        chairInviteReviewersRequest.setToken("adminPage");
//        assertEquals("adminPage", chairInviteReviewersRequest.getToken());
//        assertNotEquals("nobody", chairInviteReviewersRequest.getToken());
//    }
//
//    @Test
//    void setMessage() {
//        chairInviteReviewersRequest.setMessage("adminPage");
//        assertEquals("adminPage", chairInviteReviewersRequest.getMessage());
//        assertNotEquals("nobody", chairInviteReviewersRequest.getMessage());
//    }
//
//    @Test
//    void setConferenceId() {
//        chairInviteReviewersRequest.setConferenceId(3L);
//        assertEquals(3L, chairInviteReviewersRequest.getConferenceId());
//        assertNotEquals(2L, chairInviteReviewersRequest.getConferenceId());
//    }
//
//    @Test
//    void setReviewer() {
//        chairInviteReviewersRequest.setReviewer("adminPage");
//        assertEquals("adminPage", chairInviteReviewersRequest.getReviewer());
//        assertNotEquals("nobody", chairInviteReviewersRequest.getReviewer());
//    }
//
//    @Test
//    void testToString() {
//        assertEquals("ChairSendInvitationRequest{name='INVITE', username='username', conferenceId=2, message='message', reviewer='reviewer', conferenceFullName='conferenceFullName'}", chairInviteReviewersRequest.toString());
//        assertNotEquals("ChairSendInvitationRequest{name=INVITE, username='username'}",
//                chairInviteReviewersRequest.toString());
//    }
//}