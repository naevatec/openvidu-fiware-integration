package io.openvidu.fiware.integration.models.openvidu.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.StringJoiner;

/**
 * Model for a signal request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenViduSignalRequest {
    private String session;
    private String type;
    private String data;
    private List<String> to;

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<String> getTo() {
        return to;
    }

    public void setTo(List<String> to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenViduSignalRequest.class.getSimpleName() + "[", "]")
                .add("session='" + session + "'").add("type='" + type + "'").add("data='" + data + "'").add("to=" + to)
                .toString();
    }
}
