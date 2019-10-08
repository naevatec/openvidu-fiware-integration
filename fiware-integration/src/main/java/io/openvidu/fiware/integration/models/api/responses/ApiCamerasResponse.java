package io.openvidu.fiware.integration.models.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.openvidu.fiware.integration.controllers.CameraController;
import io.openvidu.fiware.integration.models.api.ApiCameraModel;

import java.util.List;
import java.util.StringJoiner;

/**
 * Response for the {@link CameraController#getAllCameras}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiCamerasResponse {
    private List<ApiCameraModel> cameras;

    public ApiCamerasResponse() {
    }

    public List<ApiCameraModel> getCameras() {
        return cameras;
    }

    public void setCameras(List<ApiCameraModel> cameras) {
        this.cameras = cameras;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ApiCamerasResponse.class.getSimpleName() + "[", "]").add("cameras=" + cameras)
                .toString();
    }
}
