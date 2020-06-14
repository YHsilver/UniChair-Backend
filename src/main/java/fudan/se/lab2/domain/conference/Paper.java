package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.service.UtilityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import javax.persistence.*;
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
    public enum Status {CONTRIBUTION, REVIEWING, REVIEWED, CHECKED}

    // default status CONTRIBUTION
    private Status status = Status.CONTRIBUTION;
    // pdf file
    private File file;
    private String fileName;

    // three allocated reviewers
    @OrderColumn(name = "id")
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private List<User> reviewers = new ArrayList<>();
    // three comments from reviewers
    private Boolean[] isReviewed = new Boolean[REVIEWER_NUM];
    private Boolean[] isReviewChecked = new Boolean[REVIEWER_NUM];
    private Boolean[] isRebuttalChecked = new Boolean[REVIEWER_NUM];

    private String[] comments = new String[REVIEWER_NUM];
    // three grades from reviewers
    private Integer[] grades = new Integer[REVIEWER_NUM];
    // three confidence from reviewers
    //public enum Confidence {VERY_LOW, LOW, HIGH, VERY_HIGH}
    private String[] confidences = new String[REVIEWER_NUM];
    private String[] topics;

    // empty constructor
    public Paper() {
    }

    //the first discuss post after reviewed
    @OneToMany
    private List<PaperPosts> post1 = new ArrayList<>();
    //the second discuss post after rebuttal
    @OneToMany
    private List<PaperPosts> post2 = new ArrayList<>();
    //author's rebuttal message
    private String rebuttal;


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

    public String[] getTopics() {
        return topics;
    }

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

    public List<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(ArrayList<User> reviewers) {
        this.reviewers = reviewers;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    public Integer[] getGrades() {
        return grades;
    }

    public void setGrades(Integer[] grades) {
        this.grades = grades;
    }

    public Boolean[] getIsReviewed() {
        return isReviewed;
    }

    public void setIsReviewed(Boolean[] isReviewed) {
        this.isReviewed = isReviewed;
    }

    public String[] getConfidences() {
        return confidences;
    }

    public void setConfidences(String[] confidences) {
        this.confidences = confidences;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<JSONObject> getJSONPost1() {
        List<JSONObject> list = new ArrayList<>();

        for (PaperPosts paperPosts : post1) {
            list.add(paperPosts.tojSON());
        }
        return list;
    }

    public void setPost1(List<PaperPosts> post1) {
        this.post1 = post1;
    }

    public List<JSONObject> getJSONPost2() {
        List<JSONObject> list = new ArrayList<>();

        for (PaperPosts paperPosts : post2) {
            list.add(paperPosts.tojSON());
        }
        return list;
    }

    public void setPost2(List<PaperPosts> post2) {
        this.post2 = post2;
    }

    public String getRebuttal() {
        return rebuttal;
    }

    public void setRebuttal(String rebuttal) {
        this.rebuttal = rebuttal;
    }

    public Boolean[] getIsReviewChecked() {
        return isReviewChecked;
    }

    public void setIsReviewChecked(Boolean[] isReviewChecked) {
        this.isReviewChecked = isReviewChecked;
    }

    public Boolean[] getIsRebuttalChecked() {
        return isRebuttalChecked;
    }

    public void setIsRebuttalChecked(Boolean[] isRebuttalChecked) {
        this.isRebuttalChecked = isRebuttalChecked;
    }

    public void addOneToPost1(PaperPosts comment) {
        this.post1.add(comment);
    }

    public void addOneToPost2(PaperPosts comment) {
        this.post2.add(comment);
    }

    private boolean isRebuttal() {
        return rebuttal != null;
    }

    public boolean isPass() {
        for (int i = 0; i < REVIEWER_NUM; i++) {
            if (grades[i] == null || grades[i] < 0)
                return false;
        }
        return true;
    }

    public boolean isRebuttalAllChecked() {
        for (Boolean isReviewChecked : isReviewChecked) {
            if (isReviewChecked == null || !isReviewChecked)
                return false;
        }
        return true;
    }

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
                ", isRebuttal=" + isRebuttal() +
                ", isReviewed=" + Arrays.toString(isReviewed) +
                ", comments=" + Arrays.toString(comments) +
                ", grades=" + Arrays.toString(grades) +
                ", confidences=" + Arrays.toString(confidences) +
                ", topics=" + Arrays.toString(topics) +
                ", isReviewChecked=" + Arrays.toString(isReviewChecked) +
                ", isRebuttalChecked=" + Arrays.toString(isRebuttalChecked) +
                '}';
    }

    public boolean isAllReviewed() {
        for (Boolean isReviewed : isReviewed) {
            if (isReviewed == null || !isReviewed)
                return false;
        }
        return true;
    }

    public boolean isAllChecked() {
        for (Boolean isChecked : isReviewChecked) {
            if (isChecked == null || !isChecked)
                return false;
        }
        return true;
    }

    private int isCurrPCMemberReviewed(Long reviewerId) {
        return isCurrPCOperated(reviewerId, isReviewed);
    }

    private int isCurrPCMemberChecked(Long reviewerId) {
        return isCurrPCOperated(reviewerId, isReviewChecked);
    }

    private int isCurrPCMemberRebuttalChecked(Long reviewerId) {
        return isCurrPCOperated(reviewerId, isRebuttalChecked);
    }


    private int isCurrPCOperated(Long reviewerId, Boolean[] isOperated) {
        for (int i = 0; i < REVIEWER_NUM; i++) {
            User reviewer = reviewers.get(i);
            if (reviewer != null && reviewer.getId().equals(reviewerId) && isOperated[i] != null && isOperated[i]) {
                return i;
            }
        }
        return -1;
    }


    public JSONObject toBriefJson() {
        return toBriefJson(null);
    }

    public JSONObject toBriefJson(Long reviewerId) {
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
            if (reviewerId != null) {
                str += ", \"isCurrPCMemberReviewed\":" + (isCurrPCMemberReviewed(reviewerId) != -1);
            }
            str += '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject toStandardJson() {
        return toStandardJson(null);
    }

    public JSONObject toStandardJson(Long reviewerId) {
        try {
            Long[] reviewerIds = new Long[REVIEWER_NUM];
            String[] reviewerFullNames = new String[REVIEWER_NUM];
            int i = 0;
            for (User reviewer : reviewers
            ) {
                if (reviewer != null) {
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
                    ",  \"topics\":" + UtilityService.getJsonStringFromArray(topics) +
                    ", \"title\":\"" + title + '\"' +
                    ", \"summary\":\"" + summary + '\"' +
                    ", \"authors\":" + getAuthorsObjectArray() +
                    ", \"status\":\"" + status + '\"' +
                    ", \"fileName\":\"" + fileName + '\"' +
                    ", \"fileSize\":\"" + file.length() + '\"';
            if (reviewerId != null) {
                int reId = isCurrPCMemberReviewed(reviewerId);
                str += ", \"isCurrPCMemberReviewed\":\"" + (reId != -1) + "\"";
                if (reId != -1) {
                    str += ", \"myGrade\":\"" + grades[reId] + "\"" +
                            ", \"myComment\":\"" + comments[reId] + '\"' +
                            ", \"myConfidence\":\"" + confidences[reId] + '\"';
                }
                int ckId = isCurrPCMemberChecked(reviewerId);
                str += ", \"isCurrPCMemberChecked\":\"" + (ckId != -1) + "\"";

            }
            if (status == Status.REVIEWED) {
                if (!isPass()) {
                    int reckId = isCurrPCMemberRebuttalChecked(reviewerId);
                    str += ", \"isCurrPCMemberRebuttalChecked\":\"" + (reckId != -1) + "\"";
                }

                str += ", \"grades\":" + UtilityService.getJsonStringFromArray(grades) +
                        ", \"comments\":" + UtilityService.getJsonStringFromArray(comments) +
                        ", \"confidences\":" + UtilityService.getJsonStringFromArray(confidences) +
                        ", \"isRebuttal\":\"" + isRebuttal() + "\"" +
                        ", \"isPass\":\"" + isPass() + "\"";

            }
            str += '}';
            return UtilityService.String2Json(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAuthorsObjectArray() {
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
