package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity;

public class ReviewerGetRebuttalRequest {
    private String token;
    private Long paperId;

    public ReviewerGetRebuttalRequest() {
    }

    public ReviewerGetRebuttalRequest(String token, Long paperId) {
        this.token = token;
        this.paperId = paperId;
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
}
