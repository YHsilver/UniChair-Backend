package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

import fudan.se.lab2.domain.conference.Paper;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

/**
 * @author hyf
 * 这个类用于提交投稿
 */

public class UserSubmitPaperRequest {

    private String token;
    private Long conferenceId;
    private String[] topics;
    private String title;
    private String[][] authors;
    private String summary;
    private MultipartFile file;

    public UserSubmitPaperRequest(){}

    public UserSubmitPaperRequest(String token, Long conferenceId, String[] topics, String title, String[][] authors, String summary, MultipartFile file
                                  ) {
        this.token = token;
        this.conferenceId = conferenceId;
        this.topics = topics;
        this.title = title;
        this.authors = authors;
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
    public String[] getTopics() { return topics; }
    public void setTopics(String[] topics) { this.topics = topics; }
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
    public String[][] getAuthors() { return authors; }
    public void setAuthors(String[][] authors) { this.authors = authors; }

    @Override
    public String toString() {
        return "UserSubmitPaperRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", topics=" + Arrays.toString(topics) +
                ", title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", summary='" + summary + '\'' +
                ", file=" + file +
                '}';
    }
}
