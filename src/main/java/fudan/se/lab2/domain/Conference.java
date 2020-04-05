package fudan.se.lab2.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hyf
 * 会议类
 */

@Entity
public class Conference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // 会议ID
    private Long conferenceId;

    // 会议简称
    private String conferenceAbbreviation;

    // 会议全称
    private String conferenceFullName;

    // 举办时间
    private Date conferenceTime;

    // 举办地点
    private String conferenceLocation;

    // 投稿开始日期
    private Date contributeStartTime;

    // 投稿截止日期
    private Date contributeEndTime;

    // 评审结果发布日期
    private Date resultReleaseTime;

    // 会议申请状态:PENDING(待审核), PASS(通过), REJECT(驳回)
    private enum Status {PENDING, PASS, REJECT}

    // 会议申请状态
    private Status status;

    // 会议阶段：preparation(准备中), contribution(投稿中), reviewing(审稿中), grading(终评中) and ending(审稿结束)
    public enum Stage {preparation, contribution, reviewing, grading, ending}

    // 会议阶段
    private Stage stage;

    // users in the conference
    @ManyToOne
    private User chairMan;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> authorSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> reviewerSet = new HashSet<>();

    // all the papers
    private ArrayList<Paper> paperList = new ArrayList<Paper>();

    // empty constructor
    public Conference() {
    }

    // constructor
    public Conference(User chairMan, String conferenceAbbreviation, String conferenceFullName, Date conferenceTime,
                      String conferenceLocation, Date contributeEndTime, Date resultReleaseTime) {
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

//    public void setConferenceId(Long conferenceId) {
//        this.conferenceId = conferenceId;
//    }

    public String getConferenceAbbreviation() {
        return conferenceAbbreviation;
    }

//    public void setConferenceAbbreviation(String conferenceAbbreviation) {
//        this.conferenceAbbreviation = conferenceAbbreviation;
//    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

//    public void setConferenceFullName(String conferenceFullName) {
//        this.conferenceFullName = conferenceFullName;
//    }

    public String getStatus() {
        return status.toString();
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getStage() {
        return stage.toString();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Date getContributeStartTime() {
        return contributeStartTime;
    }

//    public void setContributeStartTime(Date contributeStartTime) {
//        this.contributeStartTime = contributeStartTime;
//    }

    public Date getContributeEndTime() {
        return contributeEndTime;
    }

//    public void setContributeEndTime(Date contributeEndTime) {
//        this.contributeEndTime = contributeEndTime;
//    }

    public User getChairMan() {
        return chairMan;
    }

//    public void setChairMan(User chairMan) {
//        this.chairMan = chairMan;
//    }

    public Set<User> getAuthorSet() {
        return authorSet;
    }

//    public void setAuthorSet(Set<User> authorSet) {
//        this.authorSet = authorSet;
//    }

    public Set<User> getReviewerSet() {
        return reviewerSet;
    }

//    public void setReviewerSet(Set<User> reviewerSet) {
//        this.reviewerSet = reviewerSet;
//    }

    public ArrayList<Paper> getPaperList() {
        return paperList;
    }

//    public void setPaperList(ArrayList<Paper> paperList) {
//        this.paperList = paperList;
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

    public Date getResultReleaseTime() {
        return resultReleaseTime;
    }

//    public void setResultReleaseTime(Date resultReleaseTime) {
//        this.resultReleaseTime = resultReleaseTime;
//    }

    public String toJsonObject() {
        return "Conference{" +
                "conferenceId=" + conferenceId +
                ", conferenceAbbreviation='" + conferenceAbbreviation + '\'' +
                ", conferenceFullName='" + conferenceFullName + '\'' +
                ", conferenceTime=" + conferenceTime +
                ", conferenceLocation='" + conferenceLocation + '\'' +
                ", stage='" + stage + '\'' +
                ", contributeStartTime=" + contributeStartTime +
                ", contributeEndTime=" + contributeEndTime +
                ", resultReleaseTime=" + resultReleaseTime +
                ", chairMan=" + chairMan +
                ", authorSet=" + authorSet +
                ", reviewerSet=" + reviewerSet +
                ", paperList=" + paperList +
                '}';
    }
}

