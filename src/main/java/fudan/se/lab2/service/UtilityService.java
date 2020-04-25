package fudan.se.lab2.service;

import fudan.se.lab2.domain.conference.Conference;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UtilityService {
    public static List<JSONObject> getJSONObjectListFromConferenceSet(Set<Conference> conferenceSet, boolean isBrief){
        List<JSONObject> list = new ArrayList<>();
        if(isBrief){
            for (Conference conference:conferenceSet
            ) {
                list.add(conference.toBriefJson());
            }
        }else{
            for (Conference conference:conferenceSet
            ) {
                list.add(conference.toFullJson());
            }
        }
        return list;
    }

}
