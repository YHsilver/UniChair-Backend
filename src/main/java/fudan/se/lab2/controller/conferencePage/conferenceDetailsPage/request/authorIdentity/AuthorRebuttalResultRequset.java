package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.authorIdentity;

public class AuthorRebuttalResultRequset {
    private String token;
    private String rebuttal;
    private Long paperId;

    public AuthorRebuttalResultRequset() {
    }

    public AuthorRebuttalResultRequset(String token, String rebuttal, Long paperId) {
        this.token = token;
        this.rebuttal = rebuttal;
        this.paperId = paperId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRebuttal() {
        return rebuttal;
    }

    public void setRebuttal(String rebuttal) {
        this.rebuttal = rebuttal;
    }

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }
}
