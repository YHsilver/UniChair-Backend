package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.Paper;
import fudan.se.lab2.domain.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author pxy、yh
 * <p>
 * 会议类（the new one）
 */

@Entity
public class ConferenceNew extends BasicConference {

    @Id
    // 会议ID
    private Long conferenceId = super.getConferenceId();

    // 会议简称
    private String conferenceAbbreviation = super.getConferenceAbbreviation();

    // 会议全称
    private String conferenceFullName = super.getConferenceFullName();

    // 举办时间
    private LocalDate conferenceTime = super.getConferenceTime();

    // 举办地点
    private String conferenceLocation = super.getConferenceLocation();

    // 投稿开始日期
    private LocalDate contributeStartTime = super.getContributeStartTime();

    // 投稿截止日期
    private LocalDate contributeEndTime = super.getContributeEndTime();

    // 评审结果发布日期
    private LocalDate resultReleaseTime = super.getResultReleaseTime();

    // chairMan in the conference
    @ManyToOne
    private User chairMan = super.getChairMan();

    // 会议申请状态类别:PENDING(待审核), PASS(通过), REJECT(驳回)
    public enum Status {PENDING, PASS, REJECT}

    // 会议申请状态
    private Status status;

    // 会议阶段：preparation(准备中), contribution(投稿中), reviewing(审稿中), grading(终评中) and ending(审稿结束)
    public enum Stage {PREPARATION, CONTRIBUTION, REVIEWING, GRADING, ENDING}

    // 会议阶段
    private Stage stage;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> authorSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> reviewerSet = new HashSet<>();

    // all the papers
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Paper> paperSet = new HashSet<>();

    public ConferenceNew() {

    }

    public ConferenceNew(User chairMan, String conferenceAbbreviation, String conferenceFullName, LocalDate conferenceTime,
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
        this.paperSet = null;// 还没有人投稿
    }

    @Override
    public Long getConferenceId() {
        return conferenceId;
    }

    @Override
    public String getConferenceFullName() {
        return conferenceFullName;
    }

    @Override
    public String getConferenceLocation() {
        return conferenceLocation;
    }

    @Override
    public String getConferenceAbbreviation() {
        return conferenceAbbreviation;
    }

    @Override
    public LocalDate getContributeStartTime() {
        return contributeStartTime;
    }

    @Override
    public User getChairMan() {
        return chairMan;
    }

    @Override
    public LocalDate getResultReleaseTime() {
        return resultReleaseTime;
    }

    @Override
    public LocalDate getContributeEndTime() {
        return contributeEndTime;
    }

    @Override
    public LocalDate getConferenceTime() {
        return conferenceTime;
    }

    @Override
    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
    }

    @Override
    public void setResultReleaseTime(LocalDate resultReleaseTime) {
        this.resultReleaseTime = resultReleaseTime;
    }

    @Override
    public void setContributeStartTime(LocalDate contributeStartTime) {
        this.contributeStartTime = contributeStartTime;
    }

    @Override
    public void setContributeEndTime(LocalDate contributeEndTime) {
        this.contributeEndTime = contributeEndTime;
    }

    @Override
    public void setConferenceTime(LocalDate conferenceTime) {
        this.conferenceTime = conferenceTime;
    }

    @Override
    public void setConferenceLocation(String conferenceLocation) {
        this.conferenceLocation = conferenceLocation;
    }

    @Override
    public void setChairMan(User chairMan) {
        this.chairMan = chairMan;
    }

    @Override
    public void setConferenceAbbreviation(String conferenceAbbreviation) {
        this.conferenceAbbreviation = conferenceAbbreviation;
    }

    public Status getStatus() {
        return status;
    }

    public Stage getStage() {
        return stage;
    }

    public Set<User> getAuthorSet() {
        return authorSet;
    }

    public Set<User> getReviewerSet() {
        return reviewerSet;
    }

    public Set<Paper> getPaperSet() {
        return paperSet;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setAuthorSet(Set<User> authorSet) {
        this.authorSet = authorSet;
    }

    public void setPaperSet(Set<Paper> paperSet) {
        this.paperSet = paperSet;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setReviewerSet(Set<User> reviewerSet) {
        this.reviewerSet = reviewerSet;
    }

    // print all info, not safe!
    @Override
    public String toString() {
        return "Conference{" +
                "conferenceId=" + super.getConferenceId() +
                ", conferenceAbbreviation='" + super.getConferenceAbbreviation() + '\'' +
                ", conferenceFullName='" + super.getConferenceFullName() + '\'' +
                ", conferenceTime=" + super.getConferenceTime() +
                ", conferenceLocation='" + super.getConferenceLocation() + '\'' +
                ", contributeStartTime=" + super.getContributeStartTime() +
                ", contributeEndTime=" + super.getContributeEndTime() +
                ", resultReleaseTime=" + super.getResultReleaseTime() +
                ", status=" + status +
                ", stage=" + stage +
                ", chairMan=" + super.getChairMan().getUsername() +
                ", authorSet=" + authorSet +
                ", reviewerSet=" + reviewerSet +
                ", paperSet=" + paperSet.toString() +
                '}';
    }

    /**
     * change a JSON String 2 JSONObject
     * public static!!!
     *
     * @param str
     * @return
     * @throws ParseException
     */
    public static JSONObject String2Json(String str) throws ParseException {
        return (JSONObject) (new JSONParser().parse(str));
    }

    public JSONObject toStandardJson() {
        try {
            String str = "{" +
                    "\"id\":\"" + super.getConferenceId().toString() + '\"' +
                    ", \"abbr\":\"" + super.getConferenceAbbreviation().toString() + '\"' +
                    ", \"name\":\"" + super.getConferenceFullName().toString() + '\"' +
                    ", \"time\":\"" + super.getConferenceTime().toString() + '\"' +
                    ", \"place\":\"" + super.getConferenceLocation().toString() + '\"' +
                    ", \"contributeEndTime\":\"" + super.getContributeEndTime().toString() + '\"' +
                    ", \"resultReleaseTime\":\"" + super.getResultReleaseTime().toString() + '\"' +
                    ", \"status\":\"" + status.toString() + '\"' +
                    ", \"stage\":\"" + stage.toString() + '\"' +
                    ", \"chairMan\":\"" + super.getChairMan().getUsername().toString() + '\"' +
                    '}';
            return String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

