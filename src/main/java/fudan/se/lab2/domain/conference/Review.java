package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 会议ID
    private Long reviewId;

    @ManyToOne
    private Conference conference;

    @ManyToOne
    private User reviewer;

    @ManyToMany
    private Set<Paper> papers;

    public Review() {
    }

    public Review(Conference conference, User reviewer, Set<Paper> papers) {
        this.conference = conference;
        this.reviewer = reviewer;
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
}
