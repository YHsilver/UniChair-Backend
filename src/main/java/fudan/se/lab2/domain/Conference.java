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

    // 14个属性？？？这个类要拆
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

    // 会议申请状态类别:PENDING(待审核), PASS(通过), REJECT(驳回)
    public enum Status {PENDING, PASS, REJECT}

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

    public Status getStatus() {
        return status;
    }

    public Stage getStage() {
        return stage;
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

    public Set<User> getAuthorSet() {
        return authorSet;
    }

    public Set<User> getReviewerSet() {
        return reviewerSet;
    }

    public ArrayList<Paper> getPaperList() {
        return paperList;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setConferenceAbbreviation(String conferenceAbbreviation) {
        this.conferenceAbbreviation = conferenceAbbreviation;
    }

    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
    }

    public void setAuthorSet(Set<User> authorSet) {
        this.authorSet = authorSet;
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

    public void setPaperList(ArrayList<Paper> paperList) {
        this.paperList = paperList;
    }

    public void setResultReleaseTime(LocalDate resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setReviewerSet(Set<User> reviewerSet) {
        this.reviewerSet = reviewerSet;
    }

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

