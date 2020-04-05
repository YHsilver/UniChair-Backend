package fudan.se.lab2.controller.request;

import java.util.Date;

/**
 * @author xxx
 * 这个类用来处理会议申请
 */
public class ConferenceRequest {

    // 7个参数（含token）
    // token
    private String token;

    // abbr
    private String conferenceAbbreviation;

    // full name
    private String conferenceFullName;

    // 举办时间
    private Date conferenceTime;

    // 举办地点
    private String conferenceLocation;

    // 投稿截止日期
    private Date contributeEndTime;

    // 评审结果发布日期
    private Date resultReleaseTime;

    // empty constructor
    public ConferenceRequest() {
    }

    // constructor
    public ConferenceRequest(String token, String conferenceAbbreviation, String conferenceFullName,
                             Date conferenceTime, String conferenceLocation, Date contributeEndTime,
                             Date resultReleaseTime) {
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

    public Date getResultReleaseTime() {
        return resultReleaseTime;
    }

//    public void setResultReleaseTime(Date resultReleaseTime) {
//        this.resultReleaseTime = resultReleaseTime;
//    }

//    public void setConferenceFullName(String conferenceFullName) {
//        this.conferenceFullName = conferenceFullName;
//    }

    public Date getConferenceTime() {
        return conferenceTime;
    }

//    public void setConferenceTime(Date conferenceTime) {
//        this.conferenceTime = conferenceTime;
//    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

//    public void setConferenceLocation(String conferenceLocation) {
//        this.conferenceLocation = conferenceLocation;
//    }


    public Date getContributeEndTime() {
        return contributeEndTime;
    }

//    public void setContributeEndTime(Date contributeEndTime) {
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
