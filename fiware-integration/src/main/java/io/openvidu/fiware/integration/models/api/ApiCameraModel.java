package io.openvidu.fiware.integration.models.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.StringJoiner;

/**
 * Model for a Camera in the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCameraModel {
    private String cameraUuid;
    private String cameraAddress;

    @Pattern(regexp = "^(webrtc|rtsp)$", message = "The protocol is not supported")
    private String protocol;
    private String address;

    @NotNull
    @NotEmpty
    private String filter;

    @NotNull
    private List<String> events;
    private String description;
    private String OVToken;
    private boolean active;
    private String creationDate;

    public ApiCameraModel() {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOVToken() {
        return OVToken;
    }

    public void setOVToken(String OVToken) {
        this.OVToken = OVToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiCameraModel.class.getSimpleName() + "[", "]")
                .add("cameraUuid='" + cameraUuid + "'").add("protocol='" + protocol + "'")
                .add("address='" + address + "'").add("cameraAddress='" + cameraAddress + "'")
                .add("filter='" + filter + "'").add("events=" + events).add("description='" + description + "'")
                .add("OVToken='" + OVToken + "'").add("active=" + active).add("creationDate='" + creationDate + "'")
                .toString();
    }
}
