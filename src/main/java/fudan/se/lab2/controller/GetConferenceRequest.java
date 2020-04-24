package fudan.se.lab2.controller;

import fudan.se.lab2.domain.User;
import fudan.se.lab2.domain.conference.Conference;

public class GetConferenceRequest {
    Long conferenceId = -1L;
    String conferenceAbbreviation = "*";
    String conferenceFullName = "*";
    User chair = null;
    User reviewer = null;
    User author = null;
    Conference.Stage stage = null;
    Conference.Status status = null;
    boolean brief = false;

    public GetConferenceRequest(){}

    public Long getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getConferenceAbbreviation() {
        return conferenceAbbreviation;
    }

    public void setConferenceAbbreviation(String conferenceAbbreviation) {
        this.conferenceAbbreviation = conferenceAbbreviation;
    }

    public String getConferenceFullName() {
        return conferenceFullName;
    }

    public void setConferenceFullName(String conferenceFullName) {
        this.conferenceFullName = conferenceFullName;
    }

    public User getChair() {
        return chair;
    }

    public void setChair(User chair) {
        this.chair = chair;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Conference.Stage getStage() {
        return stage;
    }

    public void setStage(Conference.Stage stage) {
        this.stage = stage;
    }

    public Conference.Status getStatus() {
        return status;
    }

    public void setStatus(Conference.Status status) {
        this.status = status;
    }

    public boolean isBrief() {
        return brief;
    }

    public void setBrief(boolean brief) {
        this.brief = brief;
    }

}
