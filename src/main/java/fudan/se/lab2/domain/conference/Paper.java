package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.io.Serializable;
import java.util.*;

/**
 * @author hyf
 * 这个类用来处理投稿的论文
 */

@Entity
public class Paper implements Serializable {

    public static final int REVIEWER_NUM = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long paperId;

    @ManyToOne
    private Conference conference;

    @ManyToOne
    private User author;

    private String title;
    private String[][] paperAuthors;
    private String summary;
    // review status(CONTRIBUTION->Conference.Stage.CONTRIBUTION)
    public enum Status {CONTRIBUTION, REVIEWING, REVIEWED}
    // default status CONTRIBUTION
    private Status status = Status.CONTRIBUTION;
    // pdf file
    private File file;
    private String fileName;

    // three allocated reviewers
    @OrderColumn(name="id")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<User> reviewers = new ArrayList<>();
    // three comments from reviewers
    private Boolean[] isReviewed = new Boolean[REVIEWER_NUM];
    private String[] comments = new String[REVIEWER_NUM];
    // three grades from reviewers
    private Integer[] grades = new Integer[REVIEWER_NUM];
    // three confidence from reviewers
    //public enum Confidence {VERY_LOW, LOW, HIGH, VERY_HIGH}
    private String[] confidences = new String[REVIEWER_NUM];
    private String[] topics;
    // empty constructor
    public Paper() {}

    public Paper(Conference conference, User author, String title, String[][] paperAuthors, String summary, File file, String[] topics) {
        this.conference = conference;
        this.author = author;
        this.title = title;
        this.paperAuthors = paperAuthors;
        this.summary = summary;
        this.file = file;
        this.topics = topics;
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
    public String[] getTopics() { return topics; }
    public void setTopics(String[] topics) {
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
    public List<User> getReviewers() { return reviewers; }
    public void setReviewers(ArrayList<User> reviewers) { this.reviewers = reviewers; }
    public String[] getComments() { return comments; }
    public void setComments(String[] comments) { this.comments = comments; }
    public Integer[] getGrades() { return grades; }
    public void setGrades(Integer[] grades) { this.grades = grades; }
    public Boolean[] getIsReviewed() { return isReviewed; }
    public void setIsReviewed(Boolean[] isReviewed) { this.isReviewed = isReviewed; }
    public String[] getConfidences() { return confidences; }
    public void setConfidences(String[] confidences) { this.confidences = confidences; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    @Override
    public String toString() {
        return "Paper{" +
                "paperId=" + paperId +
                ", conference=" + conference +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", paperAuthors=" + Arrays.toString(paperAuthors) +
                ", summary='" + summary + '\'' +
                ", status=" + status +
                ", fileSize=" + file.length() +
                ", reviewers=" + reviewers +
                ", isReviewed=" + Arrays.toString(isReviewed) +
                ", comments=" + Arrays.toString(comments) +
                ", grades=" + Arrays.toString(grades) +
                ", confidences=" + Arrays.toString(confidences) +
                ", topics=" + Arrays.toString(topics) +
                '}';
    }

    public boolean isAllReviewed(){
        for(Boolean isReviewed: isReviewed){
            if(isReviewed == null || !isReviewed)
                return false;
        }
        return true;
    }

    public int isCurrPCMemberReviewed(Long reviewerId){
        for (int i = 0; i < REVIEWER_NUM; i++) {
            User reviewer = reviewers.get(i);
            if(reviewer != null && reviewer.getId().equals(reviewerId) && isReviewed[i] != null && isReviewed[i]){
                return i;
            }
        }
        return -1;
    }

    public JSONObject toBriefJson(){
        return toBriefJson(null);
    }

    public JSONObject toBriefJson(Long reviewerId){
        try {
            String str = "{" +
                    "\"paperId\":\"" + paperId + '\"' +
                    ", \"conferenceId\":\"" + conference.getConferenceId() + '\"' +
                    ", \"conferenceFullName\":\"" + conference.getConferenceFullName() + '\"' +
                    ", \"authorId\":\"" + author.getId() + '\"' +
                    ", \"authorFullName\":\"" + author.getFullName() + '\"' +
                    ", \"topics\":" + UtilityService.getJsonStringFromArray(topics) +
                    ", \"title\":\"" + title + '\"' +
                    ", \"summary\":\"" + summary + '\"' +
                    ", \"fileName\":\"" + fileName + '\"' +
                    ", \"status\":\"" + status + '\"';
            if(reviewerId != null){
                str += ", \"isCurrPCMemberReviewed\":" + (isCurrPCMemberReviewed(reviewerId) != -1);
            }
            str += '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject toStandardJson(){
        return toStandardJson(null);
    }

    public JSONObject toStandardJson(Long reviewerId){
        try {
            Long[] reviewerIds = new Long[REVIEWER_NUM];
            String[] reviewerFullNames = new String[REVIEWER_NUM];
            int i = 0;
            for (User reviewer: reviewers
                 ) {
                if(reviewer != null){
                    reviewerIds[i] = reviewer.getId();
                    reviewerFullNames[i] = reviewer.getFullName();
                    i++;
                }
            }

            String str = "{" +
                    "\"paperId\":\"" + paperId + '\"' +
                    ", \"conferenceId\":\"" + conference.getConferenceId() + '\"' +
                    ", \"conferenceAbbreviation\":\"" + conference.getConferenceAbbreviation() + '\"' +
                    ", \"conferenceFullName\":\"" + conference.getConferenceFullName() + '\"' +
                    ", \"authorId\":\"" + author.getId() + '\"' +
                    ", \"authorFullName\":\"" + author.getFullName() + '\"' +
                    ", \"reviewerIds\":" + UtilityService.getJsonStringFromArray(reviewerIds) +
                    ", \"reviewerFullNames\":" + UtilityService.getJsonStringFromArray(reviewerFullNames) +
                    ", \"topics\":" + UtilityService.getJsonStringFromArray(topics) +
                    ", \"title\":\"" + title + '\"' +
                    ", \"summary\":\"" + summary + '\"' +
                    ", \"authors\":" + getAuthorsObjectArray() +
                    ", \"status\":\"" + status + '\"' +
                    ", \"fileName\":\"" + fileName + '\"' +
                    ", \"fileSize\":\"" + file.length() + '\"' ;
            if(reviewerId != null){
                int reId = isCurrPCMemberReviewed(reviewerId);
                str += ", \"isCurrPCMemberReviewed\":" + (reId != -1);
                if(reId != -1){
                    str += ", \"myGrade\":" + grades[reId] +
                            ", \"myComment\":\"" + comments[reId] + '\"' +
                            ", \"myConfidence\":\"" + confidences[reId] + '\"';
                }
            }
            if(status == Status.REVIEWED){
                str += ", \"grades\":" + UtilityService.getJsonStringFromArray(grades) +
                        ", \"comments\":" + UtilityService.getJsonStringFromArray(comments) +
                        ", \"confidences\":" + UtilityService.getJsonStringFromArray(confidences);

            }
            str += '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAuthorsObjectArray(){
        StringBuilder result = new StringBuilder("[");

        for (String[] author : paperAuthors) {
            result.append('{').append("\"name\":\"").append(author[0]).append("\",\"unit\":\"").append(author[1])
                    .append("\",\"area\":\"").append(author[2]).append("\",\"email\":\"").append(author[3]).append("\"},");
        }
        if (result.length() > 2) {
            result = new StringBuilder(result.substring(0, result.length() - 1));
        }
        result.append(']');
        return result.toString();
    }
}
