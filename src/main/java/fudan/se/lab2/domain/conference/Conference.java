package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * @author pxy、yh
 * 会议类
 */

@Entity
public class Conference implements Serializable {

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

    // 介绍
    private String introduction;

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
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Paper> paperSet = new HashSet<>();

    //private ConferenceAbstract conferenceAbstract;

    // empty constructor
    public Conference() {
        //this.conferenceAbstract = new ConferenceAbstract();
        //this.conferenceAbstract.setConferenceId(this.conferenceId);
    }

    // constructor
    public Conference(User chairMan, String conferenceAbbreviation, String conferenceFullName, String conferenceLocation,
                      LocalDate conferenceTime, LocalDate contributeStartTime, LocalDate contributeEndTime,
                      LocalDate resultReleaseTime, String introduction) {
        this.chairMan = chairMan;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeStartTime = contributeStartTime;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
        this.introduction = introduction;
        this.status = Status.PENDING;// 初始化都是待审核状态
        this.stage = Stage.PREPARATION;// 初始化都是准备状态
        this.authorSet = new HashSet<>();// 还没有人投稿
        this.reviewerSet = new HashSet<>();// 还没有人成为PC members
        this.paperSet = new HashSet<>();// 还没有人投稿
        //this.conferenceAbstract = new ConferenceAbstract(this.conferenceId, conferenceAbbreviation, conferenceFullName,
                //conferenceLocation, conferenceTime, contributeStartTime, contributeEndTime, resultReleaseTime,
                //introduction, this.status, this.stage);
    }

    public void addAuthor(User author){
        this.authorSet.add(author);
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

    public String getIntroduction() {
        return introduction;
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

    public Set<Paper> getPaperSet() {
        return paperSet;
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

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public void setContributeEndTime(LocalDate contributeEndTime) {
        this.contributeEndTime = contributeEndTime;
    }

    public void setContributeStartTime(LocalDate contributeStartTime) {
        this.contributeStartTime = contributeStartTime;
    }

    public void setPaperSet(Set<Paper> paperSet) {
        this.paperSet = paperSet;
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

    // print all info, not safe!
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
                ", paperSet=" + paperSet +
                '}';
    }

    public boolean isNextStage(Stage tarStage){
        switch (stage){
            case PREPARATION:
                return tarStage == Stage.CONTRIBUTION;
            case CONTRIBUTION:
                return  tarStage == Stage.REVIEWING;
            case REVIEWING:
                return  tarStage == Stage.GRADING;
            case GRADING:
                return  tarStage == Stage.ENDING;
            case ENDING:
                return false;
        }
        return false;
    }

    /**
     * change a JSON String 2 JSONObject
     * public static!!!
     *
     * @param str JSON 格式字符串
     * @return JSON 对象
     * @throws ParseException 出错啦
     */
    public static JSONObject String2Json(String str) throws ParseException {
        return (JSONObject) (new JSONParser().parse(str));
    }

    public JSONObject toBriefJson() {
        try {
            String str = "{" +
                    "\"id\":\"" + conferenceId.toString() + '\"' +
                    ", \"abbr\":\"" + conferenceAbbreviation.toString() + '\"' +
                    ", \"name\":\"" + conferenceFullName.toString() + '\"' +
                    ", \"time\":\"" + conferenceTime.toString() + '\"' +
                    ", \"place\":\"" + conferenceLocation.toString() + '\"' +
                    ", \"contributeEndTime\":\"" + contributeEndTime.toString() + '\"' +
                    ", \"resultReleaseTime\":\"" + resultReleaseTime.toString() + '\"' +
                    ", \"status\":\"" + status.toString() + '\"' +
                    ", \"stage\":\"" + stage.toString() + '\"' +
                    ", \"chairMan\":\"" + chairMan.getUsername().toString() + '\"' +
                    '}';
            return String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject toFullJson() {
//         ["Dr. Chen", "Dr. Zhang", "Hu YuFeng", "Pan XingYu", "Yan Hua"]
        String reviewers = "";
        for (User reviewer : reviewerSet) {
            reviewers += reviewer.getFullName() + ", ";
        }
        if (reviewers.length() > 2) {
            reviewers = reviewers.substring(0, reviewers.length() - 2);
        }
        System.out.println(reviewers);
        StringBuilder authors = new StringBuilder();
        for (User author : authorSet) {
            authors.append(author.getFullName()).append(", ");
        }
        if (authors.length() > 2) {
            authors = new StringBuilder(authors.substring(0, authors.length() - 2));
        }
        try {
            String str = "{" +
                    "\"id\":\"" + conferenceId.toString() + '\"' +
                    ", \"abbreviation\":\"" + conferenceAbbreviation + '\"' +
                    ", \"fullName\":\"" + conferenceFullName + '\"' +
                    ", \"stage\":\"" + stage.toString() + '\"' +
                    ", \"chair\":\"" + chairMan.getFullName() + '\"' +
                    ", \"PCMember\":\"" + reviewers + '\"' +
                    ", \"Author\":\"" + authors.toString() + '\"' +
                    ", \"heldDate\":\"" + conferenceTime.toString() + '\"' +
                    ", \"heldPlace\":\"" + conferenceLocation + '\"' +
                    ", \"submissionDeadline\":\"" + contributeEndTime.toString() + '\"' +
                    ", \"submissionDate\":\"" + contributeStartTime.toString() + '\"' +
                    ", \"releaseDate\":\"" + resultReleaseTime.toString() + '\"' +
                    ", \"introduction\":\"" + introduction + '\"' +
                    '}';
            return String2Json(str);
        } catch (
                ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
