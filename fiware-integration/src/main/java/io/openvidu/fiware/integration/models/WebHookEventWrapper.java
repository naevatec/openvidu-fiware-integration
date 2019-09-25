package io.openvidu.fiware.integration.models;

import io.openvidu.fiware.integration.controllers.OpenViduWebHookController;

import javax.validation.constraints.Pattern;
import java.util.StringJoiner;

/**
 * Wrapper for all OpenVidu events that the {@link OpenViduWebHookController} receives.
 */
public class WebHookEventWrapper {
    private String sessionId;
    private String participantId;

    @Pattern(regexp = "^(INBOUND|OUTBOUND)$", message = "The connection is incorrect")
    private String connection;
    private String event;

    public WebHookEventWrapper() {
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WebHookEventWrapper.class.getSimpleName() + "[", "]")
                .add("sessionId='" + sessionId + "'").add("participantId='" + participantId + "'")
                .add("connection='" + connection + "'").add("event='" + event + "'").toString();
    }
}
