package io.openvidu.fiware.integration.models.orion;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.StringJoiner;

/**
 * Model for a Camera in the API.
 */
public class OrionCameraModel extends OrionModel {
    private String cameraUuid;

    @Pattern(regexp = "^(webrtc)$")
    private String protocol;
    private String address;
    private String filter;
    private List<String> events;
    private String OVToken;

    public OrionCameraModel(String id, String type, String cameraUuid, @Pattern(regexp = "^(webrtc)$") String protocol,
            String address, String filter, List<String> events, String OVToken) {
        super(id, type);
        this.cameraUuid = cameraUuid;
        this.protocol = protocol;
        this.address = address;
        this.filter = filter;
        this.events = events;
        this.OVToken = OVToken;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getOVToken() {
        return OVToken;
    }

    public void setOVToken(String OVToken) {
        this.OVToken = OVToken;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrionCameraModel.class.getSimpleName() + "[", "]")
                .add("cameraUuid='" + cameraUuid + "'").add("protocol='" + protocol + "'")
                .add("address='" + address + "'").add("filter='" + filter + "'").add("events=" + events)
                .add("OVToken='" + OVToken + "'").toString();
    }
}
