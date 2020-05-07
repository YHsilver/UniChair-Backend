package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

public class AuthorModifyPaperRequest {

    private String token;
    private Long paperId;
    // all these things will be reset, also validation check is needed
    private String[] topics;
    private String title;
    private String[][] authors;
    private String summary;
    // for pdf file, null indicates no modification
    private MultipartFile file;

    public AuthorModifyPaperRequest() {}

    public AuthorModifyPaperRequest(String token, Long paperId,
                                    String[] topics, String title, String[][] authors,
                                    String summary, MultipartFile file) {
        this.token = token;
        this.paperId = paperId;
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

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
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

    public String[][] getAuthors() {
        return authors;
    }

    public void setAuthors(String[][] authors) {
        this.authors = authors;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "AuthorModifyPaperRequest{" +
                "token='" + token + '\'' +
                ", paperId=" + paperId +
                ", topics=" + Arrays.toString(topics) +
                ", title='" + title + '\'' +
                ", authors=" + Arrays.toString(authors) +
                ", summary='" + summary + '\'' +
                ", file=" + file +
                '}';
    }
}
