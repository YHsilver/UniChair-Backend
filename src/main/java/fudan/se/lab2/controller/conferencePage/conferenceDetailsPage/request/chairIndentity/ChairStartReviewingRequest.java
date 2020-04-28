package fudan.se.lab2.controller.conferencePage.conferenceDetailsPage.request.chairIndentity;

import fudan.se.lab2.domain.conference.Conference;

public class ChairStartReviewingRequest {

    private String token;
    private Long conferenceId;
    public enum Strategy {TOPIC_RELATED, RANDOM};
    private Strategy strategy;

    public ChairStartReviewingRequest() {}

    public ChairStartReviewingRequest(String token, Long conferenceId, Strategy strategy) {
        this.token = token;
        this.conferenceId = conferenceId;
        this.strategy = strategy;
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

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        return "ChairStartReviewingRequest{" +
                "token='" + token + '\'' +
                ", conferenceId=" + conferenceId +
                ", strategy=" + strategy +
                '}';
    }
}
