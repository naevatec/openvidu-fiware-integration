package io.openvidu.fiware.integration.models.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.StringJoiner;

/**
 * Model for a Camera in the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCameraModel {
    private String cameraUuid;

    @Pattern(regexp = "^(webrtc|rtsp)$", message = "The protocol is not supported")
    private String protocol;
    private String address;
    private String cameraAddress;
    private String filter;
    private List<String> events;
    private String OVToken;

    public ApiCameraModel() {
    }

    public ApiCameraModel(String cameraUuid,
            @Pattern(regexp = "^(webrtc|rtsp)$", message = "The protocol is not supported") String protocol,
            String address, String cameraAddress, String filter, List<String> events, String OVToken) {
        this.cameraUuid = cameraUuid;
        this.protocol = protocol;
        this.address = address;
        this.cameraAddress = cameraAddress;
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

    public String getOVToken() {
        return OVToken;
    }

    public void setOVToken(String OVToken) {
        this.OVToken = OVToken;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiCameraModel.class.getSimpleName() + "[", "]")
                .add("cameraUuid='" + cameraUuid + "'").add("protocol='" + protocol + "'")
                .add("address='" + address + "'").add("cameraAddress='" + cameraAddress + "'")
                .add("filter='" + filter + "'").add("events=" + events).add("OVToken='" + OVToken + "'").toString();
    }
}
