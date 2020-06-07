package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.reviewerIdentity;


public class ReviewerSubmitPaperReviewedRequest {
    private String token;
    private Long paperId;
    private Integer grade;
    private String comment;
    private String confidence;

    public ReviewerSubmitPaperReviewedRequest() {
    }

    public ReviewerSubmitPaperReviewedRequest(String token, Long paperId, Integer grade,
                                              String comment, String confidence) {
        this.token = token;
        this.paperId = paperId;
        this.grade = grade;
        this.comment = comment;
        this.confidence = confidence;
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "ReviewerSubmitPaperReviewedRequest{" +
                "token='" + token + '\'' +
                ", paperId=" + paperId +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
