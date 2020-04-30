package fudan.se.lab2.service;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.domain.conference.Review;
import fudan.se.lab2.domain.conference.Topic;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;

import static fudan.se.lab2.domain.conference.Conference.Stage.*;

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
            if(stage == CONTRIBUTION){
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

    public static Conference.Stage getNextStage(Conference.Stage stage){
        switch (stage){
            case PREPARATION:
                return CONTRIBUTION;
            case CONTRIBUTION:
                return REVIEWING;
            case REVIEWING:
                return GRADING;
            case GRADING:
                return ENDING;
            case ENDING:
                return null;
        }
        return null;
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

    /*
     * 检查authors数组是否合法
     *
     * */
    public static boolean isAuthorsValid(String[][] authors){
        for(String[] author: authors){
            if(author.length != 4 || !(UtilityService.checkStringLength(author[0], 1) && UtilityService.checkStringLength(author[1], 1)
                    && UtilityService.checkStringLength(author[2], 1) && UtilityService.checkEmail(author[3]))){
                return false;
            }
        }
        return false;
    }

    public static boolean paperAssignment_TOPIC_RELATED(Conference conference){
        Set<Review> reviews = conference.getReviewerAndPapersMap();
        for(Paper paper : conference.getPaperSet()){
            Set<User> allValidReviewers = new HashSet<>();
            for(Topic topic : paper.getTopics()){
                allValidReviewers.addAll(topic.getReviewers());
            }
            if(allValidReviewers.size() < 3){
                allValidReviewers = conference.getReviewerSet();
            }

            Set<User> selectedReviewers = selectObjectsFromBaseSet(allValidReviewers, 3);
            if(selectedReviewers == null){
                return false;
            }
            for(User reviewer : selectedReviewers){
                conference.getPapersOfReviewer(reviewer).add(paper);
            }
        }

        return true;
    }

    private static <T> Set<T> selectObjectsFromBaseSet(Set<T> baseSet, int num){
        if(num < 0){
            return null;
        }
        if(num == 0){
            return new HashSet<>();
        }
        if(num >= baseSet.size()){
            return new HashSet<>(baseSet);
        }
        Random random = new Random();
        List<T> copyBaseSet = new ArrayList<>(baseSet);
        Set<T> resultSet = new HashSet<>();
        for(int i = 0; i < num; i++){
            T selectedObject = copyBaseSet.get(random.nextInt(copyBaseSet.size()));
            resultSet.add(selectedObject);
            copyBaseSet.remove(selectedObject);
        }
        return resultSet;
    }

    public static boolean paperAssignment_RANDOM(Conference conference){
        Random random = new Random();
        List<User> reviewersCopy = new ArrayList<>(conference.getReviewerSet());
        List<Paper> papersCopy = new ArrayList<>(conference.getPaperSet());
        int average = papersCopy.size() / reviewersCopy.size();
        for(User reviewer: reviewersCopy){
            Set<Paper> paperSet = conference.getPapersOfReviewer(reviewer);
            for(int i = 0; i < average; i++){
                Paper randomPaper = papersCopy.get(random.nextInt(papersCopy.size()));
                paperSet.add(randomPaper);
                papersCopy.remove(randomPaper);
            }
        }
        for(Paper paper: papersCopy){
            User randomReviewer = reviewersCopy.get(random.nextInt(reviewersCopy.size()));
            conference.getPapersOfReviewer(randomReviewer).add(paper);
            //conference.getReviewerAndPapersMap().get(randomReviewer).add(paper);
            reviewersCopy.remove(randomReviewer);
        }
        return true;
    }

}