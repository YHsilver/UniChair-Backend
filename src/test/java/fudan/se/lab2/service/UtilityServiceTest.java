package fudan.se.lab2.service;

import fudan.se.lab2.domain.conference.Conference;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UtilityServiceTest {

    @Test
    void isConferenceChangeStageValid() {


    }

    @Test
    void getNextStage() {
     assertEquals(Conference.Stage.CONTRIBUTION,UtilityService.getNextStage(Conference.Stage.PREPARATION));
        assertEquals(Conference.Stage.REVIEWING,UtilityService.getNextStage(Conference.Stage.CONTRIBUTION));

        assertEquals(Conference.Stage.REVIEWED,UtilityService.getNextStage(Conference.Stage.REVIEWING));
        assertEquals(Conference.Stage.ENDING,UtilityService.getNextStage(Conference.Stage.REVIEWED));
        assertNull(UtilityService.getNextStage(Conference.Stage.ENDING));

    }

    @Test
    void selectObjectsFromBaseSet() {
        Set<String> set=new HashSet<>();

        set.add("str1");
        set.add("str2");
        set.add("str3");

        Set<String> newSet=UtilityService.selectObjectsFromBaseSet(set,2);
        assertNotNull(newSet);
        newSet.retainAll(set);
        assertEquals(2, newSet.size());




    }
}