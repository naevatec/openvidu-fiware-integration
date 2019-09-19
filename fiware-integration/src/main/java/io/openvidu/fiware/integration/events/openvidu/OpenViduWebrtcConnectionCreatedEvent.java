package io.openvidu.fiware.integration.events.openvidu;

import io.openvidu.fiware.integration.events.OpenViduEvent;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

/**
 * OpenVidu event when the connection is created.
 */
public class OpenViduWebrtcConnectionCreatedEvent extends OpenViduEvent {
    @NotEmpty
    private String participantId;

    @NotEmpty
    private String connection;

    @NotEmpty
    private String receivingFrom;
    private boolean audioEnabled;
    private boolean videoEnabled;

    @NotEmpty
    private String videoSource;

    @Min(1)
    private int videoFramerate;

    @Pattern(regexp = "^\\d+x\\d+$", message = "The video dimensions are not correct")
    private String videoDimensions;

    public OpenViduWebrtcConnectionCreatedEvent(String sessionId, Long timestamp, String participantId,
            String connection, String receivingFrom, boolean audioEnabled, boolean videoEnabled, String videoSource,
            int videoFramerate,
            @Pattern(regexp = "^\\d+x\\d+$", message = "The video dimensions are not correct") String videoDimensions) {
        super(sessionId, timestamp);
        this.participantId = participantId;
        this.connection = connection;
        this.receivingFrom = receivingFrom;
        this.audioEnabled = audioEnabled;
        this.videoEnabled = videoEnabled;
        this.videoSource = videoSource;
        this.videoFramerate = videoFramerate;
        this.videoDimensions = videoDimensions;
    }

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getReceivingFrom() {
        return receivingFrom;
    }

    public void setReceivingFrom(String receivingFrom) {
        this.receivingFrom = receivingFrom;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }

    public void setVideoEnabled(boolean videoEnabled) {
        this.videoEnabled = videoEnabled;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }

    public int getVideoFramerate() {
        return videoFramerate;
    }

    public void setVideoFramerate(int videoFramerate) {
        this.videoFramerate = videoFramerate;
    }

    public String getVideoDimensions() {
        return videoDimensions;
    }

    public void setVideoDimensions(String videoDimensions) {
        this.videoDimensions = videoDimensions;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenViduWebrtcConnectionCreatedEvent.class.getSimpleName() + "[", "]")
                .add("participantId='" + participantId + "'").add("connection='" + connection + "'")
                .add("receivingFrom='" + receivingFrom + "'").add("audioEnabled=" + audioEnabled)
                .add("videoEnabled=" + videoEnabled).add("videoSource='" + videoSource + "'")
                .add("videoFramerate=" + videoFramerate).add("videoDimensions='" + videoDimensions + "'").toString();
    }
}
