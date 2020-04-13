package fudan.se.lab2.controller.request.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class UserSubmitPaperRequestTest {

    UserSubmitPaperRequest request = new UserSubmitPaperRequest();

    @Test
    void setAuthor() {
        request.setAuthor("author");
        assertEquals("author", request.getAuthor());
        assertNotEquals("aa", request.getAuthor());
    }

    @Test
    void setConferenceId() {
        request.setConferenceId(2L);
        assertEquals(2L, request.getConferenceId());
        assertNotEquals(3L, request.getConferenceId());
    }

    @Test
    void setTitle() {
        request.setTitle("title");
        assertEquals("title", request.getTitle());
        assertNotEquals("aa", request.getTitle());
    }

    @Test
    void setSummary() {
        request.setSummary("author");
        assertEquals("author", request.getSummary());
        assertNotEquals("aa", request.getSummary());
    }

    @Test
    void setToken() {
        request.setToken("example");
        assertEquals("example", request.getToken());
        assertNotEquals("aa", request.getToken());
    }

    @Test
    void testToString() {
        assertNotEquals("", request.toString());
    }
}