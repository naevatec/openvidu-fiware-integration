package io.openvidu.fiware.integration.models.openvidu.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.StringJoiner;

/**
 * Model for a filter request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenViduFilterRequest {
    private String sessionId;
    private String streamId;
    private String type;
    private String eventType;
    private String options;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenViduFilterRequest.class.getSimpleName() + "[", "]")
                .add("sessionId='" + sessionId + "'").add("streamId='" + streamId + "'").add("type='" + type + "'")
                .add("eventType='" + eventType + "'").add("options='" + options + "'").toString();
    }
}
