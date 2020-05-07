package fudan.se.lab2.domain.conference;
import fudan.se.lab2.domain.User;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Review implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 会议ID
    private Long reviewId;

    @ManyToOne
    private Conference conference;

    @ManyToOne
    private User reviewer;

    private String[] topics;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Paper> papers = new HashSet<>();

    public Review() {}

    public Review(Conference conference, User reviewer, String[] topics) {
        this.conference = conference;
        this.reviewer = reviewer;
        this.topics = topics;
    }

    public Review(Conference conference, User reviewer, String[] topics, Set<Paper> papers) {
        this.conference = conference;
        this.reviewer = reviewer;
        this.topics = topics;
        this.papers = papers;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public Set<Paper> getPapers() {
        return papers;
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }
}
