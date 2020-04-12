package fudan.se.lab2.controller.request.user;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hyf
 * 这个类用于提交投稿
 */

public class UserSubmitPaperRequest {

    // 5个属性
    // 投稿人
    private String author;

    // 所属会议
    private Long conferenceId;

    // 标题
    private String title;

    //摘要
    private String summary;

    // 论文文件
    private MultipartFile file;

    private String token;

    // empty constructor
    public UserSubmitPaperRequest() {
    }

    // constructor
    public UserSubmitPaperRequest(String author, Long conferenceId, String title, String summary, MultipartFile file) {
        this.author = author;
        this.conferenceId = conferenceId;
        this.title = title;
        this.summary = summary;
        this.file = file;
    }

    public String getAuthor() {
        return author;
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

    public MultipartFile getFile() {
        return file;
    }

    public String getToken() {
        return token;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserSubmitPaperRequest{" +
                "author='" + author + '\'' +
                ", conferenceId=" + conferenceId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", file=" + file +
                '}';
    }
}
