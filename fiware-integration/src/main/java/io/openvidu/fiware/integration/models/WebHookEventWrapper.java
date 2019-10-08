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

    // For filters
    private String filterType;
    private String eventType;
    private String data;

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

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WebHookEventWrapper.class.getSimpleName() + "[", "]")
                .add("sessionId='" + sessionId + "'").add("participantId='" + participantId + "'")
                .add("connection='" + connection + "'").add("event='" + event + "'")
                .add("filterType='" + filterType + "'").add("eventType='" + eventType + "'").add("data='" + data + "'")
                .toString();
    }
}
