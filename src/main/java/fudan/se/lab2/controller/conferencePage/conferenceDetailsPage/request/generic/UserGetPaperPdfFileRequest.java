package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.generic;

public class UserGetPaperPdfFileRequest {
    private Long paperId;

    public UserGetPaperPdfFileRequest() {}

    public UserGetPaperPdfFileRequest(Long paperId) {
        this.paperId = paperId;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    @Override
    public String toString() {
        return "UserGetPaperPdfFileRequest{" +
                "paperId=" + paperId +
                '}';
    }
}
