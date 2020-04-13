package fudan.se.lab2.controller.request.chair;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChairSearchReviewersRequestTest {

    ChairSearchReviewersRequest chairSearchReviewersRequest = new ChairSearchReviewersRequest("fullName");

    @Test
    void getName() {
        assertEquals("GET", chairSearchReviewersRequest.getName());
        assertNotEquals("LOOK", chairSearchReviewersRequest.getName());
    }

    @Test
    void getFullName() {
        assertEquals("fullName", chairSearchReviewersRequest.getFullName());
        assertNotEquals("Name", chairSearchReviewersRequest.getFullName());
    }

    @Test
    void setName() {
        chairSearchReviewersRequest.setName("BOO");
        assertEquals("BOO", chairSearchReviewersRequest.getName());
        assertNotEquals("GET", chairSearchReviewersRequest.getName());
    }

    @Test
    void setFullName() {
        chairSearchReviewersRequest.setFullName("BOO");
        assertEquals("BOO", chairSearchReviewersRequest.getFullName());
        assertNotEquals("GET", chairSearchReviewersRequest.getFullName());
    }

    @Test
    void testToString() {
        assertEquals("ChairSearchReviewersRequest{name='GET', fullName='fullName'}", chairSearchReviewersRequest.toString());
    }
}