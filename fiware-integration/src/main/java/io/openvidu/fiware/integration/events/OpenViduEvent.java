package io.openvidu.fiware.integration.events;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

/**
 * The common elements of an OpenVidu element.
 */
public abstract class OpenViduEvent {
    @NotEmpty
    private String sessionId;

    @Min(1)
    private long timestamp;

    public OpenViduEvent(String sessionId, Long timestamp) {
        this.sessionId = sessionId;
        this.timestamp = timestamp;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenViduEvent.class.getSimpleName() + "[", "]")
                .add("sessionId='" + sessionId + "'").add("timestamp=" + timestamp).toString();
    }
}
