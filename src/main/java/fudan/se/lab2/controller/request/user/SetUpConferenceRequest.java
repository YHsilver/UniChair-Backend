package fudan.se.lab2.controller.request.user;

import fudan.se.lab2.controller.request.UserRequest;

import java.time.LocalDate;

/**
 * @author pxy、yh
 * 这个类用来处理会议申请
 */
public class SetUpConferenceRequest extends UserRequest {

    // 7个参数（含token）
    // token
    private String token;

    // abbr
    private String conferenceAbbreviation;

    // full name
    private String conferenceFullName;

    // 举办时间
    private LocalDate conferenceTime;

    // 举办地点
    private String conferenceLocation;

    // 投稿截止日期
    private LocalDate contributeEndTime;

    // 评审结果发布日期
    private LocalDate resultReleaseTime;

    // empty constructor
    public SetUpConferenceRequest() {
    }

    // constructor
    public SetUpConferenceRequest(String token, String conferenceAbbreviation, String conferenceFullName,
                                  LocalDate conferenceTime, String conferenceLocation, LocalDate contributeEndTime,
                                  LocalDate resultReleaseTime) {
        this.token = token;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

    public String getToken() {
        return token;
    }

    public LocalDate getResultReleaseTime() {
        return resultReleaseTime;
    }

//    public void setResultReleaseTime(LocalDate resultReleaseTime) {
//        this.resultReleaseTime = resultReleaseTime;
//    }

//    public void setConferenceFullName(String conferenceFullName) {
//        this.conferenceFullName = conferenceFullName;
//    }

    public LocalDate getConferenceTime() {
        return conferenceTime;
    }

//    public void setConferenceTime(LocalDate conferenceTime) {
//        this.conferenceTime = conferenceTime;
//    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

//    public void setConferenceLocation(String conferenceLocation) {
//        this.conferenceLocation = conferenceLocation;
//    }


    public LocalDate getContributeEndTime() {
        return contributeEndTime;
    }

//    public void setContributeEndTime(LocalDate contributeEndTime) {
//        this.contributeEndTime = contributeEndTime;
//    }

    public String getConferenceAbbreviation() {
        return conferenceAbbreviation;
    }

//    public void setConferenceAbbreviation(String conferenceAbbreviation) {
//        this.conferenceAbbreviation = conferenceAbbreviation;
//    }

    @Override
    public String toString() {
        return "\nconferenceAbbreviation: " + this.getConferenceAbbreviation()
                + "\nconferenceFullName: " + this.getConferenceFullName()
                + "\nconferenceTime: " + this.getConferenceTime()
                + "\nconferenceLocation: " + this.getConferenceLocation()
                + "\ncontributeEndTime: " + this.getContributeEndTime()
                + "\nresultReleaseTime: " + this.getResultReleaseTime();
    }

}