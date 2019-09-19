package io.openvidu.fiware.integration.models.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.openvidu.fiware.integration.controllers.CameraController;

import java.util.List;
import java.util.StringJoiner;

/**
 * Response for the {@link CameraController#getAllCameras}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCamerasResponse {
    private List<String> cameras;

    public ApiCamerasResponse() {
    }

    public ApiCamerasResponse(List<String> cameras) {
        this.cameras = cameras;
    }

    public List<String> getCameras() {
        return cameras;
    }

    public void setCameras(List<String> cameras) {
        this.cameras = cameras;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiCamerasResponse.class.getSimpleName() + "[", "]").add("cameras=" + cameras)
                .toString();
    }
}
