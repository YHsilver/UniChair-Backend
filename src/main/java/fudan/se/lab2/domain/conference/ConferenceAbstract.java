package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author hyf
 *
 * 会议类（the new one）
 */

public class ConferenceAbstract{

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setConferenceAbbreviation(String conferenceAbbreviation) {
        this.conferenceAbbreviation = conferenceAbbreviation;
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

    public void setContributeEndTime(LocalDate contributeEndTime) {
        this.contributeEndTime = contributeEndTime;
    }

    public void setResultReleaseTime(LocalDate resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setChairMan(User chairMan) {
        this.chairMan = chairMan;
    }

    public void setStatus(Conference.Status status) {
        this.status = status;
    }

    public void setStage(Conference.Stage stage) {
        this.stage = stage;
    }


    // 会议ID
    private Long conferenceId;

    // 会议简称
    private String conferenceAbbreviation;

    // 会议全称
    private String conferenceFullName;

    // 举办时间
    private LocalDate conferenceTime;

    // 举办地点
    private String conferenceLocation;

    // 投稿开始日期
    private LocalDate contributeStartTime;

    // 投稿截止日期
    private LocalDate contributeEndTime;

    // 评审结果发布日期
    private LocalDate resultReleaseTime;

    // 简介
    private String introduction;

    private User chairMan;

    // 会议申请状态
    private Conference.Status status;

    // 会议阶段
    private Conference.Stage stage;

    public ConferenceAbstract() {

    }

    public ConferenceAbstract(Long conferenceId, String conferenceAbbreviation, String conferenceFullName, String conferenceLocation, LocalDate conferenceTime,
                               LocalDate contributeStartTime, LocalDate contributeEndTime, LocalDate resultReleaseTime, String introduction, Conference.Status status, Conference.Stage stage) {
        this.conferenceId = conferenceId;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeStartTime = contributeStartTime;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
        this.introduction = introduction;
        this.status = status;
        this.stage = stage;
    }

    public String toJSONString(){
        return "{" +
                "\"id\":\"" + conferenceId + '\"' +
                ", \"abbr\":\"" + conferenceAbbreviation + '\"' +
                ", \"name\":\"" + conferenceFullName + '\"' +
                ", \"time\":\"" + conferenceTime + '\"' +
                ", \"place\":\"" + conferenceLocation + '\"' +
                ", \"contributeStartTime\":\"" + contributeStartTime + '\"' +
                ", \"contributeEndTime\":\"" + contributeEndTime + '\"' +
                ", \"resultReleaseTime\":\"" + resultReleaseTime + '\"' +
                ", \"introduction\":\"" + introduction + '\"' +
                ", \"status\":\"" + status.toString() + '\"' +
                ", \"stage\":\"" + stage.toString() + '\"' +
                ", \"chairMan\":\"" + chairMan.getUsername() + '\"' +
                '}';
    }
}
