package fudan.se.lab2.generator;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;

import java.time.LocalDate;

public class ConferenceGenerator {
    /*
    (User chairMan, String conferenceAbbreviation, String conferenceFullName, String conferenceLocation,
                      LocalDate conferenceTime, LocalDate contributeStartTime, LocalDate contributeEndTime,
                      LocalDate resultReleaseTime, String introduction)
    * */

    private final static String PERFIX = "G";
    private static int randomConferenceNum = 0;
    private final static String[] locationSet = new String[]{"Beijing", "Shanghai", "HK", "Los Angles", "New York", "Berlin", "Paris", "London", "WuHan"};

    public static Conference getRandomConference(User chairMan){
        String conferenceAbbreviation = PERFIX + randomConferenceNum + "_" + StringGenerator.getRandomString(3, 4, true, false, false).toUpperCase();
        String conferenceFullName = conferenceAbbreviation + " " + StringGenerator.getRandomString();
        String conferenceLocation = locationSet[(int)(Math.random() * locationSet.length)];
        String introduction = StringGenerator.getRandomString(80, 120, true, false, true);
        LocalDate conferenceTime = LocalDate.of(2020, (int)(Math.random() * 12) + 1, (int)(Math.random() * 28) + 1);
        LocalDate contributeStartTime = LocalDate.of(2021, (int)(Math.random() * 12) + 1, (int)(Math.random() * 28) + 1);
        LocalDate contributeEndTime = LocalDate.of(2022, (int)(Math.random() * 12) + 1, (int)(Math.random() * 28) + 1);
        LocalDate resultReleaseTime =  LocalDate.of(2022, (int)(Math.random() * 12) + 1, (int)(Math.random() * 28) + 1);
        randomConferenceNum++;
        return new Conference(chairMan, conferenceAbbreviation, conferenceFullName, conferenceLocation,
                conferenceTime, contributeStartTime, contributeEndTime, resultReleaseTime, introduction);
    }

}
