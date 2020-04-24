package fudan.se.lab2.domain.conference;

import fudan.se.lab2.domain.User;

import javax.persistence.*;
import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author hyf
 * 这个类用来处理投稿的论文
 */

@Entity
public class Paper implements Serializable {

    // 6个参数

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 论文文件 ID
    private Long fileId;

    // 投稿人
    @OneToOne
    private User author;

    // 审稿人
    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> reviewerSet = new HashSet<>();

    // 所属会议
    private Long conferenceId;

    // 标题
    private String title;

    //摘要
    private String summary;

    //审核状态, 审核通过和投稿中
    private enum Status {PASS, REVIEWING}

    // 审核状态
    private Status status;

    private File file;

    // empty constructor
    public Paper() {
    }

    // constructor
    public Paper(User author, Long conferenceId, String title, String summary, File file) {
        this.file = file;
        this.author = author;
        // 最开始没有审稿人
        this.reviewerSet = null;
        this.conferenceId = conferenceId;
        this.title = title;
        this.summary = summary;
        this.status = Status.REVIEWING;
    }

    public Long getConferenceId() {
        return conferenceId;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public Long getFileId() {
        return fileId;
    }

    public Status getStatus() {
        return status;
    }

    public User getAuthor() {
        return author;
    }

    public Set<User> getReviewerSet() {
        return reviewerSet;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setReviewerSet(Set<User> reviewerSet) {
        this.reviewerSet = reviewerSet;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "author='" + author.getUsername() + '\'' +
                ", conferenceId=" + conferenceId +
                ", title='" + title + '\'' +
                ", reviewer='" + reviewerSet.toString() + '\'' +
                ", summary='" + summary + '\'' +
                ", fileId=" + fileId +
                ", status=" + status +
                '}';
    }
}
