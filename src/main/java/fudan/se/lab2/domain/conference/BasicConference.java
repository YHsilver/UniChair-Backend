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

@Entity
public class BasicConference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // users in the conference
    @ManyToOne
    private User chairMan;

    public BasicConference() {

    }

    public BasicConference(User chairMan, String conferenceAbbreviation, String conferenceFullName, LocalDate conferenceTime,
                           String conferenceLocation, LocalDate contributeEndTime, LocalDate resultReleaseTime) {
        this.chairMan = chairMan;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public LocalDate getConferenceTime() {
        return conferenceTime;
    }

    public LocalDate getContributeEndTime() {
        return contributeEndTime;
    }

    public String getConferenceAbbreviation() {
        return conferenceAbbreviation;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

    public LocalDate getContributeStartTime() {
        return contributeStartTime;
    }

    public String getConferenceLocation() {
        return conferenceLocation;
    }

    public User getChairMan() {
        return chairMan;
    }

    public LocalDate getResultReleaseTime() {
        return resultReleaseTime;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setConferenceAbbreviation(String conferenceAbbreviation) {
        this.conferenceAbbreviation = conferenceAbbreviation;
    }

    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
    }

    public void setChairMan(User chairMan) {
        this.chairMan = chairMan;
    }

    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    public void setConferenceTime(LocalDate conferenceTime) {
        this.conferenceTime = conferenceTime;
    }

    public void setContributeEndTime(LocalDate contributeEndTime) {
        this.contributeEndTime = contributeEndTime;
    }

    public void setContributeStartTime(LocalDate contributeStartTime) {
        this.contributeStartTime = contributeStartTime;
    }

    public void setResultReleaseTime(LocalDate resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    @Override
    public String toString() {
        return "BasicConference{" +
                "conferenceId=" + conferenceId +
                ", conferenceAbbreviation='" + conferenceAbbreviation + '\'' +
                ", conferenceFullName='" + conferenceFullName + '\'' +
                ", conferenceTime=" + conferenceTime +
                ", conferenceLocation='" + conferenceLocation + '\'' +
                ", contributeStartTime=" + contributeStartTime +
                ", contributeEndTime=" + contributeEndTime +
                ", resultReleaseTime=" + resultReleaseTime +
                ", chairMan=" + chairMan +
                '}';
    }
}
