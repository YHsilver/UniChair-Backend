package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.applicationPage.request.UserAddConferenceApplicationRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author hyf
 * this is a test class for UserAddConferenceApplicationRequest
 */

class UserAddConferenceApplicationRequestTest {

    // suppose that 'gaokao' is a big meeting
    private UserAddConferenceApplicationRequest userAddConferenceApplicationRequest
            = new UserAddConferenceApplicationRequest("token", "conferenceAbbreviation",
            "conferenceFullName", LocalDate.of(2020, 7, 7),
            "conferenceLocation", LocalDate.of(2020, 7, 8),
            LocalDate.of(2020, 7, 8), LocalDate.of(2020, 7, 9),
            "introduction");

    @Test
    void testGetToken() {
        assertEquals("token", userAddConferenceApplicationRequest.getToken());
        assertNotEquals("noToken", userAddConferenceApplicationRequest.getToken());
    }

    @Test
    void testGetConferenceFullName() {
        assertEquals("conferenceFullName", userAddConferenceApplicationRequest.getConferenceFullName());
        assertNotEquals("conference", userAddConferenceApplicationRequest.getConferenceFullName());
    }

    @Test
    void testGetResultReleaseTime() {
        assertEquals(LocalDate.of(2020, 7, 9), userAddConferenceApplicationRequest.getResultReleaseTime());
        assertNotEquals(LocalDate.of(2020, 6, 9), userAddConferenceApplicationRequest.getResultReleaseTime());
    }

    @Test
    void testGetConferenceTime() {
        assertEquals(LocalDate.of(2020, 7, 7), userAddConferenceApplicationRequest.getConferenceTime());
        assertNotEquals(LocalDate.of(2020, 6, 7), userAddConferenceApplicationRequest.getConferenceTime());
    }

    @Test
    void testGetConferenceLocation() {
        assertEquals("conferenceLocation", userAddConferenceApplicationRequest.getConferenceLocation());
        assertNotEquals("conferenceSpot", userAddConferenceApplicationRequest.getConferenceLocation());
    }

    @Test
    void testGetContributeEndTime() {
        assertEquals(LocalDate.of(2020, 7, 8), userAddConferenceApplicationRequest.getContributeEndTime());
        assertNotEquals(LocalDate.of(2020, 6, 8), userAddConferenceApplicationRequest.getContributeEndTime());
    }

    @Test
    void testGetConferenceAbbreviation() {
        assertEquals("conferenceAbbreviation", userAddConferenceApplicationRequest.getConferenceAbbreviation());
        assertNotEquals("conferenceAbbr", userAddConferenceApplicationRequest.getConferenceAbbreviation());
    }

    @Test
    void setToken() {
        userAddConferenceApplicationRequest.setToken("anotherToken");
        assertEquals("anotherToken", userAddConferenceApplicationRequest.getToken());
        assertNotEquals("Token", userAddConferenceApplicationRequest.getToken());
    }

    @Test
    void setConferenceFullName() {
        userAddConferenceApplicationRequest.setConferenceFullName("FOOL");
        assertEquals("FOOL", userAddConferenceApplicationRequest.getConferenceFullName());
        assertNotEquals("ConferenceFullName", userAddConferenceApplicationRequest.getConferenceFullName());
    }

    @Test
    void setConferenceTime() {
        userAddConferenceApplicationRequest.setConferenceTime(LocalDate.of(2020, 6, 7));
        assertEquals(LocalDate.of(2020, 6, 7), userAddConferenceApplicationRequest.getConferenceTime());
        assertNotEquals(LocalDate.of(2020, 7, 7), userAddConferenceApplicationRequest.getConferenceTime());
    }

    @Test
    void setConferenceLocation() {
        userAddConferenceApplicationRequest.setConferenceLocation("FuDan");
        assertEquals("FuDan", userAddConferenceApplicationRequest.getConferenceLocation());
        assertNotEquals("JiaoDa", userAddConferenceApplicationRequest.getConferenceLocation());
    }

    @Test
    void setResultReleaseTime() {
        userAddConferenceApplicationRequest.setResultReleaseTime(LocalDate.of(2020, 6, 9));
        assertEquals(LocalDate.of(2020, 6, 9), userAddConferenceApplicationRequest.getResultReleaseTime());
        assertNotEquals(LocalDate.of(2020, 7, 9), userAddConferenceApplicationRequest.getResultReleaseTime());
    }

    @Test
    void setContributeEndTime() {
        userAddConferenceApplicationRequest.setContributeEndTime(LocalDate.of(2020, 6, 8));
        assertEquals(LocalDate.of(2020, 6, 8), userAddConferenceApplicationRequest.getContributeEndTime());
        assertNotEquals(LocalDate.of(2020, 7, 8), userAddConferenceApplicationRequest.getContributeEndTime());
    }

    @Test
    void setConferenceAbbreviation() {
        userAddConferenceApplicationRequest.setConferenceAbbreviation("Abbr");
        assertEquals("Abbr", userAddConferenceApplicationRequest.getConferenceAbbreviation());
        assertNotEquals("ConferenceAbbreviation", userAddConferenceApplicationRequest.getConferenceAbbreviation());
    }

    @Test
    void testToString() {
        assertNotEquals("userAddConferenceApplicationRequest{conferenceAbbreviation='conferenceAbbreviation', " +
                "conferenceFullName='conferenceFullName', conferenceTime=2020-07-07, " +
                "conferenceLocation='conferenceLocation', contributeEndTime=2020-07-08, " +
                "resultReleaseTime=2020-07-09}", userAddConferenceApplicationRequest.toString());
        assertEquals("UserAddConferenceApplicationRequest{tokenconferenceAbbreviation='conferenceAbbreviation', conferenceFullName='conferenceFullName', conferenceTime=2020-07-07, conferenceLocation='conferenceLocation', contributeEndTime=2020-07-08, resultReleaseTime=2020-07-09}", userAddConferenceApplicationRequest.toString());
    }
}