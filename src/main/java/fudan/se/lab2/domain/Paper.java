package fudan.se.lab2.domain;

/**
 * @author hyf
 * 这个类用来处理投稿的论文
 */
public class Paper {

    // 6个参数
    // 投稿人
    private String author;

    // 所属会议
    private Long conferenceId;

    // 标题
    private String title;

    //摘要
    private String summary;

    // 论文文件 ID
    private Long fileId;

    //审核状态, 审核通过和投稿中
    private enum Status {PASS, REVIEWING}

    // 审核状态
    private Status status;

    // empty constructor
    public Paper() {
    }

    // constructor
    public Paper(String author, Long conferenceId, String title, String summary, Long fileId) {
        this.author = author;
        this.conferenceId = conferenceId;
        this.title = title;
        this.summary = summary;
        this.fileId = fileId;
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

    public String getAuthor() {
        return author;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setAuthor(String author) {
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

    @Override
    public String toString() {
        return "Paper{" +
                "author='" + author + '\'' +
                ", conferenceId=" + conferenceId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", fileId=" + fileId +
                ", status=" + status +
                '}';
    }
}
