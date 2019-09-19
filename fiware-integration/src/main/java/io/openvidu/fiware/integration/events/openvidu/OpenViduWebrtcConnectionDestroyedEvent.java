package io.openvidu.fiware.integration.events.openvidu;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

/**
 * OpenVidu event when the connection is destroyed.
 */
public class OpenViduWebrtcConnectionDestroyedEvent extends OpenViduWebrtcConnectionCreatedEvent {
    @Min(0)
    private long startTime;

    @Min(0)
    private int duration;
    private String reason;

    public OpenViduWebrtcConnectionDestroyedEvent(String sessionId, Long timestamp, String participantId,
            String connection, String receivingFrom, boolean audioEnabled, boolean videoEnabled, String videoSource,
            int videoFramerate,
            @Pattern(regexp = "^\\d+x\\d+$", message = "The video dimensions are not correct") String videoDimensions,
            @Min(0) long startTime, @Min(0) int duration, String reason) {
        super(sessionId, timestamp, participantId, connection, receivingFrom, audioEnabled, videoEnabled, videoSource,
                videoFramerate, videoDimensions);
        this.startTime = startTime;
        this.duration = duration;
        this.reason = reason;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenViduWebrtcConnectionDestroyedEvent.class.getSimpleName() + "[", "]")
                .add("startTime=" + startTime).add("duration=" + duration).add("reason='" + reason + "'").toString();
    }
}
