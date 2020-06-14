package fudan.se.lab2.service;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;
import fudan.se.lab2.domain.conference.Paper;
import fudan.se.lab2.exception.ConferencException.ChairChangeConferenceStageFailException;
import fudan.se.lab2.repository.PaperRepository;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

import static fudan.se.lab2.domain.conference.Conference.Stage.*;

public class UtilityService {
    // 日志
    public static final Random random = getSecureRandom();

    private static Random getSecureRandom() {
        Random random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Random initialize fail");
            return null;
        }
        return random;
    }

    private static Logger logger = LoggerFactory.getLogger(UtilityService.class);

    /**
     * check if all strings in topics are the topics of target conference
     *
     * @param conference target conference
     * @param topics     string array to check
     * @return true if all strings in topics are the topics of target conference
     */
    public static boolean isTopicsValidInConference(Conference conference, String[] topics) {
        Set<String> validTopics = new HashSet<>(Arrays.asList(conference.getTopics()));
        validTopics.retainAll(new HashSet<>(Arrays.asList(topics)));
        return validTopics.size() == topics.length;
    }

    public static boolean isValidReviewer(Conference conference, User reviewer) {
        if (reviewer == null || conference == null) {
            return false;
        }
        for (User tarReviewer : conference.getReviewerSet()) {
            if (tarReviewer.getId().equals(reviewer.getId())) {
                return true;
            }
        }
        return false;
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
     * @param conferenceSet set of conference to return
     * @param isBrief       whether brief information or full information should be returned
     * @return json object of conference as list
     */
    public static List<JSONObject> getJSONObjectListFromConferenceSet(Set<Conference> conferenceSet, boolean isBrief) {
        List<JSONObject> list = new ArrayList<>();
        if (isBrief) {
            for (Conference conference : conferenceSet
            ) {
                list.add(conference.toBriefJson());
            }
        } else {
            for (Conference conference : conferenceSet
            ) {
                list.add(conference.toFullJson());
            }
        }
        return list;
    }

    /**
     * @param tar       target string to check
     * @param minLength target string should be longer than minlength, default no limit for maxlength
     * @return true if satisfying limits
     */
    public static boolean checkStringLength(String tar, int minLength) {
        return checkStringLength(tar, minLength, -1);
    }

    /**
     * @param tar       target string to check
     * @param minLength target string should be longer than minlength
     * @param maxLength target string should be shorter than maxlength
     * @return true if satisfying limits
     */
    public static boolean checkStringLength(String tar, int minLength, int maxLength) {
        if (minLength < 0) {
            minLength = 0;
        }
        return !(tar == null || tar.length() < minLength || (minLength <= maxLength && tar.length() > maxLength));
    }

    /**
     * @param tar target string to check
     * @return true if satisfying email format
     */
    public static boolean checkEmail(String tar) {
        // TODO: implements the check of email format
        return checkStringLength(tar, 1, -1);
    }

    /**
     * @param conference target conference
     * @param stage      target stage to changed
     * @return true if this change can be done with satisfied limits
     */
    public static boolean isConferenceChangeStageValid(Conference conference, Conference.Stage stage, PaperRepository paperRepository) {
        if (!conference.isNextStage(stage) || stage == Conference.Stage.REVIEWING) {
            return false;
        }

        checkToReviewedStageValid(conference, stage, paperRepository);
        checkToEndingStageValid(conference, stage, paperRepository);

        return true;
    }

    private static void checkToReviewedStageValid(Conference conference, Conference.Stage stage, PaperRepository paperRepository) {
        if (stage == Conference.Stage.REVIEWED) {
            Set<Paper> papers = paperRepository.findPapersByConference(conference);
            for (Paper paper : papers) {
                for (Boolean isReviewChecked : paper.getIsReviewChecked()) {
                    if (isReviewChecked == null || !isReviewChecked)
                        throw new ChairChangeConferenceStageFailException("Not All Papers Review Checked!");
                }
            }
        }
    }

    private static void checkToEndingStageValid(Conference conference, Conference.Stage stage, PaperRepository paperRepository) {
        if (stage == Conference.Stage.ENDING) {
            Set<Paper> papers = paperRepository.findPapersByConference(conference);
            for (Paper paper : papers) {
                if (!paper.isPass() && paper.getRebuttal() != null) {
                    for (Boolean isRebuttalChecked : paper.getIsRebuttalChecked()) {
                        if (isRebuttalChecked == null || !isRebuttalChecked)
                            throw new ChairChangeConferenceStageFailException("Not All Rebuttal Papers Checked!");
                    }
                }
            }
        }
    }


    public static Conference.Stage getNextStage(Conference.Stage stage) {
        switch (stage) {
            case PREPARATION:
                return CONTRIBUTION;
            case CONTRIBUTION:
                return REVIEWING;
            case REVIEWING:
                return REVIEWED;
            case REVIEWED:
                return ENDING;
            case ENDING:
                return null;
        }
        return null;
    }

    /**
     * @param array object array to transfer
     * @return a string with json array format
     */
    public static String getJsonStringFromArray(Object[] array) {
        if (array == null || array.length == 0) {
            return "[]";
        }
        StringBuilder result = new StringBuilder("[");
        boolean isBasicData = false;
        if (array[0] instanceof Integer || array[0] instanceof Long) {
            isBasicData = true;
        }

        for (Object object : array) {
            if (object != null) {
                if (isBasicData) {
                    result.append(object.toString()).append(',');
                } else {
                    result.append('\"').append(object.toString()).append("\",");
                }
            } else {
                result.append('\"').append("null").append("\",");
            }
        }
        if (result.length() > 2) {
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        result.append(']');
        return result.toString();
    }

    /*
     * 检查authors数组是否合法
     *
     * */
    public static String[][] isAuthorsValid(String[] authors) {
        String[][] authorArrays;
        if (authors == null || authors.length == 0 || authors.length % 4 != 0) { return null; }
        authorArrays = new String[authors.length / 4][4];
        for (int i = 0; i < authors.length / 4; i++) {
            String[] author = new String[4];
            author[0] = authors[4 * i];
            author[1] = authors[4 * i + 1];
            author[2] = authors[4 * i + 2];
            author[3] = authors[4 * i + 3];
            if (!(UtilityService.checkStringLength(author[0], 1) && UtilityService.checkStringLength(author[1], 1)
                    && UtilityService.checkStringLength(author[2], 1) && UtilityService.checkEmail(author[3]))) {
                return null;
            }
            authorArrays[i] = author;
        }
        return authorArrays;
    }

    public static <T> Set<T> selectObjectsFromBaseSet(Set<T> baseSet, int num) {
        if (num < 0) { return null; }
        if (num == 0) { return new HashSet<>(); }
        if (num >= baseSet.size()) {
            return new HashSet<>(baseSet);
        }
        List<T> copyBaseSet = new ArrayList<>(baseSet);
        Set<T> resultSet = new HashSet<>();
        Random random;
        try {
            random = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            logger.trace("context", e);
            return null;
        }
        for (int i = 0; i < num; i++) {
            T selectedObject = copyBaseSet.get(random.nextInt(copyBaseSet.size()));
            resultSet.add(selectedObject);
            copyBaseSet.remove(selectedObject);
        }
        return resultSet;
    }

    public static boolean isValidReviewer(Paper paper, User reviewer) {
        if (reviewer == null || paper == null) { return false; }
        for (User tarReviewer : paper.getReviewers()) {
            if (tarReviewer.getId().equals(reviewer.getId())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidReviewerOrChair(Paper paper, User user) {
        return isValidReviewer(paper, user) || paper.getConference().getChairman().getId().equals(user.getId());
    }


}
