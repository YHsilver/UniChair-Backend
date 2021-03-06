package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
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
    // 会议 topic
    private String[] topics;

    // 会议申请状态类别:PENDING(待审核), PASS(通过), REJECT(驳回)
    public enum Status {PENDING, PASS, REJECT}
    private Status status;
    public enum Stage {PREPARATION, CONTRIBUTION, REVIEWING, REVIEWED, ENDING}
    private Stage stage;

    // users in the conference
    @ManyToOne
    private User chairman;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> authorSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> reviewerSet = new HashSet<>();

    // empty constructor
    public Conference() {
    }

    // constructor
    public Conference(User chairman, String conferenceAbbreviation, String conferenceFullName, String conferenceLocation,
                      LocalDate conferenceTime, LocalDate contributeStartTime, LocalDate contributeEndTime,
                      LocalDate resultReleaseTime, String introduction, String[] topics) {
        this.chairman = chairman;
        this.conferenceAbbreviation = conferenceAbbreviation;
        this.conferenceFullName = conferenceFullName;
        this.conferenceTime = conferenceTime;
        this.conferenceLocation = conferenceLocation;
        this.contributeStartTime = contributeStartTime;
        this.contributeEndTime = contributeEndTime;
        this.resultReleaseTime = resultReleaseTime;
        this.introduction = introduction;
        this.topics = topics;
        this.status = Status.PENDING;// 初始化都是待审核状态
        this.stage = Stage.PREPARATION;// 初始化都是准备状态
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

    public String[] getTopics() { return topics; }

    public User getChairman() {
        return chairman;
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

    public void setChairman(User chairman) {
        this.chairman = chairman;
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

    public void setTopics(String[] topics) { this.topics = topics; }

    public void setContributeEndTime(LocalDate contributeEndTime) {
        this.contributeEndTime = contributeEndTime;
    }

    public void setContributeStartTime(LocalDate contributeStartTime) {
        this.contributeStartTime = contributeStartTime;
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
                ", chairman=" + chairman +
                ", authorSet=" + authorSet +
                ", reviewerSet=" + reviewerSet +
                '}';
    }

    public boolean isNextStage(Stage tarStage){
        switch (stage){
            case PREPARATION:
                return tarStage == Stage.CONTRIBUTION;
            case CONTRIBUTION:
                return  tarStage == Stage.REVIEWING;
            case REVIEWING:
                return  tarStage == Stage.REVIEWED;
            case REVIEWED:
                return  tarStage == Stage.ENDING;
            case ENDING:
                return false;
        }
        return false;
    }

    public boolean isAfterOrEqualsStage(Stage tarStage){
        while(tarStage != null){
            if(tarStage == stage){
                return true;
            }
            tarStage = UtilityService.getNextStage(tarStage);
        }
        return false;
    }

    public JSONObject toBriefJson() {
        try {
            String str = "{" +
                    "\"id\":\"" + conferenceId.toString() + '\"' +
                    ", \"abbr\":\"" + conferenceAbbreviation + '\"' +
                    ", \"name\":\"" + conferenceFullName + '\"' +
                    ", \"time\":\"" + conferenceTime.toString() + '\"' +
                    ", \"place\":\"" + conferenceLocation + '\"' +
                    ", \"contributeEndTime\":\"" + contributeEndTime.toString() + '\"' +
                    ", \"resultReleaseTime\":\"" + resultReleaseTime.toString() + '\"' +
                    ", \"contributeStartTime\":\"" + contributeStartTime.toString() + '\"' +
                    ", \"status\":\"" + status.toString() + '\"' +
                    ", \"stage\":\"" + stage.toString() + '\"' +
                    ", \"chairman\":\"" + chairman.getUsername() + '\"' +
                    '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject toFullJson() {
        // get json array as string for all reviewers' full name
        int i = 0;
        String[] reviewerFullNames = new String[reviewerSet.size()];
        for (User reviewer : reviewerSet) {
            reviewerFullNames[i++] = reviewer.getFullName();
        }
        // get json array as string for all authors' full name
        i = 0;
        String[] authorFullNames = new String[authorSet.size()];
        for (User author : authorSet) {
            authorFullNames[i++] = author.getFullName();
        }

        try {
            String str = "{" +
                    "\"id\":\"" + conferenceId.toString() + '\"' +
                    ", \"abbreviation\":\"" + conferenceAbbreviation + '\"' +
                    ", \"fullName\":\"" + conferenceFullName + '\"' +
                    ", \"stage\":\"" + stage.toString() + '\"' +
                    ", \"chair\":\"" + chairman.getFullName() + '\"' +
                    ", \"PCMember\":" + UtilityService.getJsonStringFromArray(reviewerFullNames) +
                    ", \"Author\":" + UtilityService.getJsonStringFromArray(authorFullNames) +
                    ", \"heldDate\":\"" + conferenceTime.toString() + '\"' +
                    ", \"heldPlace\":\"" + conferenceLocation + '\"' +
                    ", \"submissionDeadline\":\"" + contributeEndTime.toString() + '\"' +
                    ", \"submissionDate\":\"" + contributeStartTime.toString() + '\"' +
                    ", \"releaseDate\":\"" + resultReleaseTime.toString() + '\"' +
                    ", \"introduction\":\"" + introduction + '\"' +
                    ", \"topics\":" + UtilityService.getJsonStringFromArray(topics) +
                    '}';
            return UtilityService.String2Json(str);
        } catch (
                ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

