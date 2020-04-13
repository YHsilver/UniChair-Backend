package fudan.se.lab2.controller.request.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author hyf
 * this is a test class for UserSetUpConferenceRequest
 */

class UserSetUpConferenceRequestTest {

    // suppose that 'gaokao' is a big meeting
    private UserSetUpConferenceRequest userSetUpConferenceRequest
            = new UserSetUpConferenceRequest("token", "conferenceAbbreviation",
            "conferenceFullName", LocalDate.of(2020, 7, 7),
            "conferenceLocation", LocalDate.of(2020, 7, 8),
            LocalDate.of(2020, 7, 8), LocalDate.of(2020, 7, 9),
            "introduction");

    @Test
    void testGetToken() {
        assertEquals("token", userSetUpConferenceRequest.getToken());
        assertNotEquals("noToken", userSetUpConferenceRequest.getToken());
    }

    @Test
    void testGetConferenceFullName() {
        assertEquals("conferenceFullName", userSetUpConferenceRequest.getConferenceFullName());
        assertNotEquals("conference", userSetUpConferenceRequest.getConferenceFullName());
    }

    @Test
    void testGetResultReleaseTime() {
        assertEquals(LocalDate.of(2020, 7, 9), userSetUpConferenceRequest.getResultReleaseTime());
        assertNotEquals(LocalDate.of(2020, 6, 9), userSetUpConferenceRequest.getResultReleaseTime());
    }

    @Test
    void testGetConferenceTime() {
        assertEquals(LocalDate.of(2020, 7, 7), userSetUpConferenceRequest.getConferenceTime());
        assertNotEquals(LocalDate.of(2020, 6, 7), userSetUpConferenceRequest.getConferenceTime());
    }

    @Test
    void testGetConferenceLocation() {
        assertEquals("conferenceLocation", userSetUpConferenceRequest.getConferenceLocation());
        assertNotEquals("conferenceSpot", userSetUpConferenceRequest.getConferenceLocation());
    }

    @Test
    void testGetContributeEndTime() {
        assertEquals(LocalDate.of(2020, 7, 8), userSetUpConferenceRequest.getContributeEndTime());
        assertNotEquals(LocalDate.of(2020, 6, 8), userSetUpConferenceRequest.getContributeEndTime());
    }

    @Test
    void testGetConferenceAbbreviation() {
        assertEquals("conferenceAbbreviation", userSetUpConferenceRequest.getConferenceAbbreviation());
        assertNotEquals("conferenceAbbr", userSetUpConferenceRequest.getConferenceAbbreviation());
    }

    @Test
    void setToken() {
        userSetUpConferenceRequest.setToken("anotherToken");
        assertEquals("anotherToken", userSetUpConferenceRequest.getToken());
        assertNotEquals("Token", userSetUpConferenceRequest.getToken());
    }

    @Test
    void setConferenceFullName() {
        userSetUpConferenceRequest.setConferenceFullName("FOOL");
        assertEquals("FOOL", userSetUpConferenceRequest.getConferenceFullName());
        assertNotEquals("ConferenceFullName", userSetUpConferenceRequest.getConferenceFullName());
    }

    @Test
    void setConferenceTime() {
        userSetUpConferenceRequest.setConferenceTime(LocalDate.of(2020, 6, 7));
        assertEquals(LocalDate.of(2020, 6, 7), userSetUpConferenceRequest.getConferenceTime());
        assertNotEquals(LocalDate.of(2020, 7, 7), userSetUpConferenceRequest.getConferenceTime());
    }

    @Test
    void setConferenceLocation() {
        userSetUpConferenceRequest.setConferenceLocation("FuDan");
        assertEquals("FuDan", userSetUpConferenceRequest.getConferenceLocation());
        assertNotEquals("JiaoDa", userSetUpConferenceRequest.getConferenceLocation());
    }

    @Test
    void setResultReleaseTime() {
        userSetUpConferenceRequest.setResultReleaseTime(LocalDate.of(2020, 6, 9));
        assertEquals(LocalDate.of(2020, 6, 9), userSetUpConferenceRequest.getResultReleaseTime());
        assertNotEquals(LocalDate.of(2020, 7, 9), userSetUpConferenceRequest.getResultReleaseTime());
    }

    @Test
    void setContributeEndTime() {
        userSetUpConferenceRequest.setContributeEndTime(LocalDate.of(2020, 6, 8));
        assertEquals(LocalDate.of(2020, 6, 8), userSetUpConferenceRequest.getContributeEndTime());
        assertNotEquals(LocalDate.of(2020, 7, 8), userSetUpConferenceRequest.getContributeEndTime());
    }

    @Test
    void setConferenceAbbreviation() {
        userSetUpConferenceRequest.setConferenceAbbreviation("Abbr");
        assertEquals("Abbr", userSetUpConferenceRequest.getConferenceAbbreviation());
        assertNotEquals("ConferenceAbbreviation", userSetUpConferenceRequest.getConferenceAbbreviation());
    }

    @Test
    void testToString() {
        assertNotEquals("userSetUpConferenceRequest{conferenceAbbreviation='conferenceAbbreviation', " +
                "conferenceFullName='conferenceFullName', conferenceTime=2020-07-07, " +
                "conferenceLocation='conferenceLocation', contributeEndTime=2020-07-08, " +
                "resultReleaseTime=2020-07-09}", userSetUpConferenceRequest.toString());
        assertEquals("UserSetUpConferenceRequest{tokenconferenceAbbreviation='conferenceAbbreviation', conferenceFullName='conferenceFullName', conferenceTime=2020-07-07, conferenceLocation='conferenceLocation', contributeEndTime=2020-07-08, resultReleaseTime=2020-07-09}", userSetUpConferenceRequest.toString());
    }
}