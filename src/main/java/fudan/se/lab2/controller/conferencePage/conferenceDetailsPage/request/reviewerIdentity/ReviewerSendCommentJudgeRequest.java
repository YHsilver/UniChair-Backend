package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity;

public class ReviewerSendCommentJudgeRequest {
    private String token;
    private Long paperId;
    private String message;

    public ReviewerSendCommentJudgeRequest() {
    }

    public ReviewerSendCommentJudgeRequest(String token, Long paperId, String message) {
        this.token = token;
        this.paperId = paperId;
        this.message = message;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
