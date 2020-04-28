package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hyf
 * 这个类用来处理投稿的论文
 */

@Entity
public class Paper implements Serializable {

    private static final int REVIEWER_NUM = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long paperId;

    @ManyToOne
    private Conference conference;

    @ManyToOne
    private User author;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> reviewerSet = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Topic> topics = new HashSet<>();

    private String title;
    private String[][] paperAuthors;
    private String summary;
    // review status(CONTRIBUTION->Conference.Stage.CONTRIBUTION)
    public enum Status {CONTRIBUTION, REVIEWING, REVIEWED}
    // default status CONTRIBUTION
    private Status status = Status.CONTRIBUTION;
    // pdf file
    private File file;

    // three allocated reviewers
    private User[] reviewers = new User[REVIEWER_NUM];
    // three comments from reviewers
    private String[] comments = new String[REVIEWER_NUM];
    // three grades from reviewers
    private Integer[] grades = new Integer[REVIEWER_NUM];
    // three confidence from reviewers
    public enum Confidence {VERY_LOW, LOW, HIGH, VERY_HIGH};
    private Confidence[] confidences = new Confidence[REVIEWER_NUM];

    // empty constructor
    public Paper() {}

    public Paper(Conference conference, User author, String title, String[][] paperAuthors, String summary, File file) {
        this.conference = conference;
        this.author = author;
        this.title = title;
        this.paperAuthors = paperAuthors;
        this.summary = summary;
        this.file = file;
    }

    public Long getPaperId() {
        return paperId;
    }
    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
    public Conference getConference() {
        return conference;
    }
    public void setConference(Conference conference) {
        this.conference = conference;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public Set<User> getReviewerSet() {
        return reviewerSet;
    }
    public void setReviewerSet(Set<User> reviewerSet) {
        this.reviewerSet = reviewerSet;
    }
    public Set<Topic> getTopics() {
        return topics;
    }
    public void setTopics(Set<Topic> topics) {
        this.topics = topics;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String[][] getPaperAuthors() {
        return paperAuthors;
    }
    public void setPaperAuthors(String[][] paperAuthors) {
        this.paperAuthors = paperAuthors;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public File getFile() {
        return file;
    }
    public void setFile(File file) {
        this.file = file;
    }
    public User[] getReviewers() { return reviewers; }
    public void setReviewers(User[] reviewers) { this.reviewers = reviewers; }
    public String[] getComments() { return comments; }
    public void setComments(String[] comments) { this.comments = comments; }
    public Integer[] getGrades() { return grades; }
    public void setGrades(Integer[] grades) { this.grades = grades; }
    public Confidence[] getConfidence() { return confidences; }
    public void setConfidence(Confidence[] confidences) { this.confidences = confidences; }

    @Override
    public String toString() {
        return "Paper{" +
                "paperId=" + paperId +
                ", conference=" + conference +
                ", author=" + author +
                ", reviewerSet=" + reviewerSet +
                ", topics=" + topics +
                ", title='" + title + '\'' +
                ", paperAuthors=" + Arrays.toString(paperAuthors) +
                ", summary='" + summary + '\'' +
                ", status=" + status +
                ", file=" + file +
                '}';
    }

    public JSONObject toBriefJson(){
        try {
            String[] topicArray = new String[topics.size()];
            int i = 0;
            for (Topic topic: topics
            ) {
                topicArray[i++] = topic.getTopic();
            }
            String str = "{" +
                    "\"paperId\":\"" + paperId + '\"' +
                    ", \"conferenceId\":\"" + conference.getConferenceId() + '\"' +
                    ", \"conferenceFullName\":\"" + conference.getConferenceFullName() + '\"' +
                    ", \"authorId\":\"" + author.getId() + '\"' +
                    ", \"authorFullName\":\"" + author.getFullName() + '\"' +
                    ", \"topics\":\"" + UtilityService.getJsonStringFromArray(topicArray) + '\"' +
                    ", \"title\":\"" + title + '\"' +
                    ", \"status\":\"" + status + '\"' +
                    '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject toStandardJson(){
        try {
            Long[] reviewerIds = new Long[reviewerSet.size()];
            String[] reviewerFullNames = new String[reviewerSet.size()];
            int i = 0;
            for (User reviewer: reviewerSet
                 ) {
                reviewerIds[i] = reviewer.getId();
                reviewerFullNames[i] = reviewer.getFullName();
                i++;
            }
            String[] topicArray = new String[topics.size()];
            i = 0;
            for (Topic topic: topics
                 ) {
                topicArray[i++] = topic.getTopic();
            }
            String str = "{" +
                    "\"paperId\":\"" + paperId + '\"' +
                    ", \"conferenceId\":\"" + conference.getConferenceId() + '\"' +
                    ", \"conferenceAbbreviation\":\"" + conference.getConferenceAbbreviation() + '\"' +
                    ", \"conferenceFullName\":\"" + conference.getConferenceFullName() + '\"' +
                    ", \"authorId\":\"" + author.getId() + '\"' +
                    ", \"authorFullName\":\"" + author.getFullName() + '\"' +
                    ", \"reviewerIds\":\"" + UtilityService.getJsonStringFromArray(reviewerIds) + '\"' +
                    ", \"reviewerFullNames\":\"" + UtilityService.getJsonStringFromArray(reviewerFullNames) + '\"' +
                    ", \"topics\":\"" + UtilityService.getJsonStringFromArray(topicArray) + '\"' +
                    ", \"title\":\"" + title + '\"' +
                    ", \"summary\":\"" + summary + '\"' +
                    ", \"status\":\"" + status + '\"' +
                    ", \"fileName\":\"" + file.getName() + '\"';
            if(status == Status.REVIEWED){
                str += ", \"grades\":\"" + UtilityService.getJsonStringFromArray(grades) + '\"' +
                        ", \"comments\":\"" + UtilityService.getJsonStringFromArray(comments) + '\"' +
                        ", \"confidences\":\"" + UtilityService.getJsonStringFromArray(confidences) + '\"';
            }
            str += '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
