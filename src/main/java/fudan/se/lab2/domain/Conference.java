package fudan.se.lab2.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author pxy、yh
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
    private LocalDate conferenceTime;

    // 举办地点
    private String conferenceLocation;

    // 投稿开始日期
    private LocalDate contributeStartTime;

    // 投稿截止日期
    private LocalDate contributeEndTime;

    // 评审结果发布日期
    private LocalDate resultReleaseTime;

    // 会议申请状态类别:PENDING(待审核), PASS(通过), REJECT(驳回),ALL(所有)
    public static enum Status {PENDING, PASS, REJECT, ALL}

    // 会议申请状态
    private Status status;

    // 会议阶段：preparation(准备中), contribution(投稿中), reviewing(审稿中), grading(终评中) and ending(审稿结束)
    public enum Stage {PREPARATION, CONTRIBUTION, REVIEWING, GRADING, ENDING}

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
    public Conference(User chairMan, String conferenceAbbreviation, String conferenceFullName, LocalDate conferenceTime,
                      String conferenceLocation, LocalDate contributeEndTime, LocalDate resultReleaseTime) {
        this.chairMan = chairMan;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
        this.status = Status.PENDING;// 初始化都是待审核状态
        this.stage = Stage.PREPARATION;// 初始化都是准备状态
        this.authorSet = null;// 还没有人投稿
        this.reviewerSet = null;// 还没有人成为PC members
        this.paperList = null;// 还没有人投稿
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

    public LocalDate getContributeStartTime() {
        return contributeStartTime;
    }

//    public void setContributeStartTime(LocalDate contributeStartTime) {
//        this.contributeStartTime = contributeStartTime;
//    }

    public LocalDate getContributeEndTime() {
        return contributeEndTime;
    }

//    public void setContributeEndTime(LocalDate contributeEndTime) {
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

    public LocalDate getConferenceTime() {
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

    public LocalDate getResultReleaseTime() {
        return resultReleaseTime;
    }

//    public void setResultReleaseTime(LocalDate resultReleaseTime) {
//        this.resultReleaseTime = resultReleaseTime;
//    }

    @Override
    public String toString() {
        return "Conference{" +
                "conferenceId=" + conferenceId +
                ", conferenceAbbreviation='" + conferenceAbbreviation + '\'' +
                ", conferenceFullName='" + conferenceFullName + '\'' +
                ", conferenceTime=" + conferenceTime +
                ", conferenceLocation='" + conferenceLocation + '\'' +
                ", contributeStartTime=" + contributeStartTime +
                ", contributeEndTime=" + contributeEndTime +
                ", resultReleaseTime=" + resultReleaseTime +
                ", status=" + status +
                ", stage=" + stage +
                ", chairMan=" + chairMan +
                ", authorSet=" + authorSet +
                ", reviewerSet=" + reviewerSet +
                ", paperList=" + paperList +
                '}';
    }
}

