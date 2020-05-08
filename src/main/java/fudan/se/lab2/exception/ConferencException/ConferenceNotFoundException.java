package fudan.se.lab2.exception.ConferencException;

/**
 * @author hyf
 * 会议仓库找会议，会议未找到
 */

public class ConferenceNotFoundException extends RuntimeException {
    public ConferenceNotFoundException() {
        super("Conference not found!");
    }
}

