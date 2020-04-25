package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author hyf
 * 这个类用于提交投稿
 */

public class UserSubmitPaperRequest {

    private String token;
    private Long conferenceId;
    private String title;
    private String summary;
    private MultipartFile file;

    public UserSubmitPaperRequest(){}

    public UserSubmitPaperRequest(String token, Long conferenceId, String title, String summary, MultipartFile file
                                  ) {
        this.token = token;
        this.conferenceId = conferenceId;
        this.title = title;
        this.summary = summary;
        this.file = file;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public Long getConferenceId() {
        return conferenceId;
    }
    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public MultipartFile getFile() { return file; }
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UserSubmitPaperRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", file=" + file +
                '}';
    }
}
