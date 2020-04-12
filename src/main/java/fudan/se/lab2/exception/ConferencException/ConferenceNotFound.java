package fudan.se.lab2.exception.ConferencException;

/**
 * @author hyf
 * 会议仓库找会议，会议未找到
 */

public class ConferenceNotFound extends RuntimeException {
    public ConferenceNotFound() {
        super("Conference not found!");
    }
}

