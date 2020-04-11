package fudan.se.lab2.controller.request.user;

import java.time.LocalDate;

/**
 * @author pxy、yh
 * 这个类用来处理会议申请
 */

public class UserSetUpConferenceRequest {

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
    public UserSetUpConferenceRequest() {
    }

    // constructor
    public UserSetUpConferenceRequest(String token, String conferenceAbbreviation, String conferenceFullName,
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

    public String getToken() {
        return token;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

    public LocalDate getResultReleaseTime() {
        return resultReleaseTime;
    }

    public LocalDate getConferenceTime() {
        return conferenceTime;
    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

    public LocalDate getContributeEndTime() {
        return contributeEndTime;
    }

    public String getConferenceAbbreviation() {
        return conferenceAbbreviation;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
    }

    public void setConferenceTime(LocalDate conferenceTime) {
        this.conferenceTime = conferenceTime;
    }

    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public void setResultReleaseTime(LocalDate resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    public void setContributeEndTime(LocalDate contributeEndTime) {
        this.contributeEndTime = contributeEndTime;
    }

    public void setConferenceAbbreviation(String conferenceAbbreviation) {
        this.conferenceAbbreviation = conferenceAbbreviation;
    }

    @Override
    public String toString() {
        return "UserSetUpConferenceRequest{" +
                token +
                "conferenceAbbreviation='" + conferenceAbbreviation + '\'' +
                ", conferenceFullName='" + conferenceFullName + '\'' +
                ", conferenceTime=" + conferenceTime +
                ", conferenceLocation='" + conferenceLocation + '\'' +
                ", contributeEndTime=" + contributeEndTime +
                ", resultReleaseTime=" + resultReleaseTime +
                '}';
    }
}