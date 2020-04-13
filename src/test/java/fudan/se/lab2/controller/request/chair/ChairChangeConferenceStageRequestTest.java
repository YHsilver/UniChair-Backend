package fudan.se.lab2.controller.request.chair;

import fudan.se.lab2.domain.Conference;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author hyf
 * this is a test class for ChairChangeConferenceStageRequest
 */

class ChairChangeConferenceStageRequestTest {

    private ChairChangeConferenceStageRequest chairChangeConferenceStageRequest =
            new ChairChangeConferenceStageRequest(Conference.Stage.CONTRIBUTION, 2L);

    @Test
    void testGetName() {
        assertEquals("CHANGESTAGE", chairChangeConferenceStageRequest.getName());
        assertNotEquals("LOOK", chairChangeConferenceStageRequest.getName());
    }

    @Test
    void testGetChangedStage() {
        assertEquals(Conference.Stage.CONTRIBUTION, chairChangeConferenceStageRequest.getChangedStage());
        assertNotEquals(Conference.Stage.ENDING, chairChangeConferenceStageRequest.getChangedStage());
    }

    @Test
    void testGetConferenceId() {
        assertEquals(2L, chairChangeConferenceStageRequest.getConferenceId());
        assertNotEquals(0L, chairChangeConferenceStageRequest.getConferenceId());
    }

    @Test
    void testSetName() {
        chairChangeConferenceStageRequest.setName("BIUBIU");
        assertEquals("BIUBIU", chairChangeConferenceStageRequest.getName());
        assertNotEquals("LOOK", chairChangeConferenceStageRequest.getName());
    }

    @Test
    void testSetConferenceId() {
        chairChangeConferenceStageRequest.setConferenceId(33L);
        assertEquals(33L, chairChangeConferenceStageRequest.getConferenceId());
        assertNotEquals(2L, chairChangeConferenceStageRequest.getConferenceId());
    }

    @Test
    void testSetChangedStage() {
        chairChangeConferenceStageRequest.setChangedStage(Conference.Stage.ENDING);
        assertEquals(Conference.Stage.ENDING, chairChangeConferenceStageRequest.getChangedStage());
        assertNotEquals(Conference.Stage.GRADING, chairChangeConferenceStageRequest.getChangedStage());
    }

    @Test
    void testToString() {
        assertNotEquals("", chairChangeConferenceStageRequest.toString());
    }
}