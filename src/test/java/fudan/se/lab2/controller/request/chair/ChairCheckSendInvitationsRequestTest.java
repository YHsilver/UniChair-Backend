//package fudan.se.lab2.controller.request.chair;
//
//import fudan.se.lab2.domain.Invitation;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotEquals;
//
///**
// * @author hyf
// * this is a test class for ChairCheckSendInvitationsRequest
// */
//
//class ChairCheckSendInvitationsRequestTest {
//
//    ChairCheckSendInvitationsRequest chairCheckSendInvitationsRequest = new ChairCheckSendInvitationsRequest("token",
//            Invitation.Status.PENDING);
//
//    @Test
//    void getToken() {
//        assertEquals("token", chairCheckSendInvitationsRequest.getToken());
//        assertNotEquals("AToken", chairCheckSendInvitationsRequest.getToken());
//    }
//
//    @Test
//    void getName() {
//        assertEquals("CHECKSENDINVITATION", chairCheckSendInvitationsRequest.getName());
//        assertNotEquals("LOOK", chairCheckSendInvitationsRequest.getName());
//    }
//
//    @Test
//    void getStatus() {
//        assertEquals(Invitation.Status.PENDING, chairCheckSendInvitationsRequest.getStatus());
//        assertNotEquals(Invitation.Status.PASS, chairCheckSendInvitationsRequest.getStatus());
//    }
//
//    @Test
//    void setToken() {
//        chairCheckSendInvitationsRequest.setToken("AToken");
//        assertEquals("AToken", chairCheckSendInvitationsRequest.getToken());
//        assertNotEquals("Token", chairCheckSendInvitationsRequest.getToken());
//    }
//
//    @Test
//    void setName() {
//        chairCheckSendInvitationsRequest.setName("LOOK");
//        assertEquals("LOOK", chairCheckSendInvitationsRequest.getName());
//        assertNotEquals("BIUBIU", chairCheckSendInvitationsRequest.getName());
//    }
//
//    @Test
//    void setStatus() {
//        chairCheckSendInvitationsRequest.setStatus(Invitation.Status.PASS);
//        assertEquals(Invitation.Status.PASS, chairCheckSendInvitationsRequest.getStatus());
//        assertNotEquals(Invitation.Status.PENDING, chairCheckSendInvitationsRequest.getStatus());
//    }
//
//    @Test
//    void testToString() {
//        assertEquals("ChairCheckSendInvitationsRequest{name='CHECKSENDINVITATION', token='token', status=PENDING}", chairCheckSendInvitationsRequest.toString());
//    }
//}