package fudan.se.lab2.controller.conferencePage.conferenceAbstractPage.request;

/**
 * @author hyf(modified by pxy)
 * 这个类用于获取通过的会议列表，以投稿
 */

public class UserGetPassedConferenceRequest {

    private String token;
    private String identity;
    // -1 indicates all conferences after startIndex
    private int startIndex;
    private int listLength;

    public UserGetPassedConferenceRequest(String token, String identity, int startIndex, int listLength){
        this.token = token;
        this.identity = identity;
        this.startIndex = startIndex;
        this.listLength = listLength;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getIdentity() { return identity; };
    public void setIdentity(String identity) { this.identity = identity; }
    public int getStartIndex() { return startIndex; }
    public void setStartIndex(int startIndex) { this.startIndex = startIndex; }
    public int getListLength() { return listLength; }
    public void setListLength(int listLength) { this.listLength = listLength; }

}


