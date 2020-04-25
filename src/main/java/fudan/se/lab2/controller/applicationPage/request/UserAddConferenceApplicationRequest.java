package fudan.se.lab2.controller.applicationPage.request;

import java.time.LocalDate;

/**
 * @author pxy、yh
 * 这个类用来处理会议申请
 */

public class UserAddConferenceApplicationRequest {

    // 8个参数（含token）
    // token
    private String token;

    private String conferenceAbbreviation;
    private String conferenceFullName;
    private String conferenceLocation;
    private LocalDate conferenceTime;
    private LocalDate contributeStartTime;
    private LocalDate contributeEndTime;
    private LocalDate resultReleaseTime;
    private String introduction;

    public UserAddConferenceApplicationRequest(){}

    public UserAddConferenceApplicationRequest(String token, String conferenceAbbreviation, String conferenceFullName,
                                               LocalDate conferenceTime, String conferenceLocation, LocalDate contributeStartTime,
                                               LocalDate contributeEndTime, LocalDate resultReleaseTime, String introduction) {
        this.token = token;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.contributeStartTime = contributeStartTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
        this.introduction = introduction;
    }

    public String getToken() {
        return token;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

    public LocalDate getContributeStartTime() {
        return contributeStartTime;
    }

    public String getIntroduction() {
        return introduction;
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

    public void setContributeStartTime(LocalDate contributeStartTime) {
        this.contributeStartTime = contributeStartTime;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
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
        return "UserAddConferenceApplicationRequest{" +
                "token='" + token + '\'' +
                ", conferenceAbbreviation='" + conferenceAbbreviation + '\'' +
                ", conferenceFullName='" + conferenceFullName + '\'' +
                ", conferenceLocation='" + conferenceLocation + '\'' +
                ", conferenceTime=" + conferenceTime +
                ", contributeStartTime=" + contributeStartTime +
                ", contributeEndTime=" + contributeEndTime +
                ", resultReleaseTime=" + resultReleaseTime +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}