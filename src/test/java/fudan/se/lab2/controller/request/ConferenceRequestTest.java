package fudan.se.lab2.controller.request;

import fudan.se.lab2.controller.request.user.UserSetUpConferenceRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author hyf
 * this is a test class for ConferenceRequest
 */
class ConferenceRequestTest {

    // suppose that 'gaokao' is a big meeting
    private UserSetUpConferenceRequest conferenceRequest = new UserSetUpConferenceRequest("token", "conferenceAbbreviation",
            "conferenceFullName",
            LocalDate.of(2020, 7, 7), "conferenceLocation", LocalDate.of(2020, 7, 8),
            LocalDate.of(2020, 7, 9));

    @Test
    void testGetConferenceFullName() {
        assertEquals("conferenceFullName", conferenceRequest.getConferenceFullName());
    }

    @Test
    void testGetToken() {
        assertEquals("token", conferenceRequest.getToken());
    }

    @Test
    void testGetResultReleaseTime() {
        assertEquals(LocalDate.of(2020, 7, 9), conferenceRequest.getResultReleaseTime());
    }

    @Test
    void testGetConferenceTime() {
        assertEquals(LocalDate.of(2020, 7, 7), conferenceRequest.getConferenceTime());
    }

    @Test
    void testGetConferenceLocation() {
        assertEquals("conferenceLocation", conferenceRequest.getConferenceLocation());
    }

    @Test
    void testGetContributeEndTime() {
        assertEquals(LocalDate.of(2020, 7, 8), conferenceRequest.getContributeEndTime());
    }

    @Test
    void testGetConferenceAbbreviation() {
        assertEquals("conferenceAbbreviation", conferenceRequest.getConferenceAbbreviation());
    }

    @Test
    void testToString() {
        assertEquals("conferenceFullName", conferenceRequest.getConferenceFullName());
    }
}