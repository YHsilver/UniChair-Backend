package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long topicId;

    @ManyToOne
    private Conference conference;
    private String topic;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> authors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> reviewers = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Paper> papers = new HashSet<>();

    public Topic() {}

    public Topic(Conference conference, String topic){
        this.conference = conference;
        this.topic = topic;
    }

    public Topic(Conference conference, String topic, Set<User> authors, Set<User> reviewers, Set<Paper> papers){
        this.conference = conference;
        this.topic = topic;
        this.authors = authors;
        this.reviewers = reviewers;
        this.papers = papers;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Set<User> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<User> authors) {
        this.authors = authors;
    }

    public Set<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(Set<User> reviewers) {
        this.reviewers = reviewers;
    }

    public Set<Paper> getPapers() {
        return papers;
    }

    public void setPapers(Set<Paper> papers) {
        this.papers = papers;
    }

    @Override
    public String toString() {
        return "Topic{" +
                "topicId=" + topicId +
                ", conference=" + conference +
                ", topic='" + topic + '\'' +
                ", authors=" + authors +
                ", reviewers=" + reviewers +
                ", papers=" + papers +
                '}';
    }
}
