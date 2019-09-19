package io.openvidu.fiware.integration.models.api.requests;

import io.openvidu.fiware.integration.controllers.CameraController;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.StringJoiner;

/**
 * Request for the {@link CameraController#createCamera}.
 */
public class ApiCameraRequest {
    private String cameraUuid;

    @Pattern(regexp = "^(webrtc|rtsp)$", message = "The protocol is not supported")
    private String protocol;
    private String cameraAddress;
    private String filter;
    private List<String> events;

    public ApiCameraRequest() {
    }

    public ApiCameraRequest(String cameraUuid,
            @Pattern(regexp = "^(webrtc|rtsp)$", message = "The protocol is not supported") String protocol,
            String cameraAddress, String filter, List<String> events) {
        this.cameraUuid = cameraUuid;
        this.protocol = protocol;
        this.cameraAddress = cameraAddress;
        this.filter = filter;
        this.events = events;
    }

    public String getCameraUuid() {
        return cameraUuid;
    }

    public void setCameraUuid(String cameraUuid) {
        this.cameraUuid = cameraUuid;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getCameraAddress() {
        return cameraAddress;
    }

    public void setCameraAddress(String cameraAddress) {
        this.cameraAddress = cameraAddress;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiCameraRequest.class.getSimpleName() + "[", "]")
                .add("cameraUuid='" + cameraUuid + "'").add("protocol='" + protocol + "'")
                .add("cameraAddress='" + cameraAddress + "'").add("filter='" + filter + "'").add("events=" + events)
                .toString();
    }
}
