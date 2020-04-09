package fudan.se.lab2.controller.request.user;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hyf
 * 这个类用于提交投稿
 */

public class UserSubmitPaperRequest {

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
