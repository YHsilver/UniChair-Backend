package fudan.se.lab2.service;

import fudan.se.lab2.domain.conference.Conference;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

public class UtilityService {

    /**
     * check if all strings in topics are the topics of target conference
     * @param conference target conference
     * @param topics string array to check
     * @return true if all strings in topics are the topics of target conference
     */
    public static boolean isTopicsValidInConference(Conference conference, String[] topics){
        Set<String> validTopics = new HashSet<>(Arrays.asList(conference.getTopics()));
        validTopics.retainAll(new HashSet<>(Arrays.asList(topics)));
        return validTopics.size() == topics.length;
    }

    /**
     * change a JSON String 2 JSONObject
     * public static!!!
     *
     * @param str JSON 格式字符串
     * @return JSON 对象
     * @throws ParseException 出错啦
     */
    public static JSONObject String2Json(String str) throws ParseException {
        return (JSONObject) (new JSONParser().parse(str));
    }

    /**
     *
     * @param conferenceSet set of conference to return
     * @param isBrief whether brief information or full information should be returned
     * @return json object of conference as list
     */
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

    /**
     *
     * @param tar target string to check
     * @param minLength target string should be longer than minlength, default no limit for maxlength
     * @return true if satisfying limits
     */
    public static boolean checkStringLength(String tar, int minLength){
        return checkStringLength(tar, minLength, -1);
    }

    /**
     *
     * @param tar target string to check
     * @param minLength target string should be longer than minlength
     * @param maxLength target string should be shorter than maxlength
     * @return true if satisfying limits
     */
    public static boolean checkStringLength(String tar, int minLength, int maxLength){
        if(minLength < 0){ minLength = 0; }
        return !(tar == null || tar.length() < minLength || (minLength <= maxLength && tar.length() > maxLength));
    }

    /**
     *
     * @param tar target string to check
     * @return true if satisfying email format
     */
    public static boolean checkEmail(String tar){
        // TODO: implements the check of email format
        return checkStringLength(tar, 1, -1);
    }

    /**
     *
     * @param conference target conference
     * @param stage target stage to changed
     * @return true if this change can be done with satisfied limits
     */
    public static boolean isConferenceChangeStageValid(Conference conference, Conference.Stage stage){
        if(conference.isNextStage(stage)){
            if(stage == Conference.Stage.CONTRIBUTION){
                // default true
                return true;
            }
            if(stage == Conference.Stage.REVIEWING){
                // TODO: find a valid paper allocation plan
                return conference.getReviewerSet().size() >= 3;
            }
            if(stage == Conference.Stage.GRADING){
                // TODO: check if all papers are reviewed by reviewers
                return false;
            }
            if(stage == Conference.Stage.ENDING){
                // default true
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param array object array to transfer
     * @return a string with json array format
     */
    public static String getJsonStringFromArray(Object[] array){
        StringBuilder result = new StringBuilder();
        for (Object object: array) {
            result.append(object.toString()).append(", ");
        }
        if (array.length > 2) {
            result = new StringBuilder(result.substring(0, result.length() - 2));
        }
        return result.toString();
    }

}
