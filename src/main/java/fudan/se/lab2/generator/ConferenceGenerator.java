package fudan.se.lab2.generator;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ConferenceGenerator {
    /*
    (User chairman, String conferenceAbbreviation, String conferenceFullName, String conferenceLocation,
                      LocalDate conferenceTime, LocalDate contributeStartTime, LocalDate contributeEndTime,
                      LocalDate resultReleaseTime, String introduction)
    * */
    private static Random random = new Random();
    private static final char[] SPACE_ARRAY = new char[]{' '};
    private final static String PERFIX = "G";
    private static int randomConferenceNum = 0;
    private final static String[] locationSet = new String[]{"Beijing", "Shanghai", "HK", "Los Angles", "New York", "Berlin", "Paris", "London", "WuHan"};

    public static Conference getRandomConference(User chairman){
        String conferenceAbbreviation = PERFIX + randomConferenceNum + "_" + StringGenerator.getRandomString(3, 4, StringGenerator.LETTERS_SET).toUpperCase();
        String conferenceFullName = conferenceAbbreviation + " " + StringGenerator.getRandomString();
        String conferenceLocation = locationSet[random.nextInt( locationSet.length)];
        String introduction = StringGenerator.getRandomString(80, 120, StringGenerator.concat(StringGenerator.LETTERS_SET, SPACE_ARRAY));
        LocalDate conferenceTime = LocalDate.of(2024, random.nextInt( 12) + 1, random.nextInt( 28) + 1);
        LocalDate contributeStartTime = LocalDate.of(2021, random.nextInt( 12) + 1, random.nextInt( 28) + 1);
        LocalDate contributeEndTime = LocalDate.of(2022, random.nextInt( 12) + 1, random.nextInt( 28) + 1);
        LocalDate resultReleaseTime =  LocalDate.of(2023, random.nextInt( 12) + 1, random.nextInt( 28) + 1);
        String[] topics = new String[random.nextInt(3) + 1];
        for(int i = 0; i < topics.length; i++){
            topics[i] = "T" + i + "_" + StringGenerator.getRandomString(4, 4);
        }
        randomConferenceNum++;
        return new Conference(chairman, conferenceAbbreviation, conferenceFullName, conferenceLocation,
                conferenceTime, contributeStartTime, contributeEndTime, resultReleaseTime, introduction, topics);
    }

    public static Set<Conference> getRandomConferences(int randomConferenceNum, Set<User> chairSet){
        Set<Conference> conferenceSet = new HashSet<>();
        User[] chairs = new User[chairSet.size()];
        chairSet.toArray(chairs);
        for(int i = 0; i < randomConferenceNum; i++){
            Conference randomConference = ConferenceGenerator.getRandomConference(chairs[random.nextInt( chairs.length)]);
            conferenceSet.add(randomConference);;
        }
        return conferenceSet;
    }

}
