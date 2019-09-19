package io.openvidu.fiware.integration.utils;

/**
 * Paths of the API-REST.
 */
public class ApiPaths {
    // Api rest
    private static final String Api = "/api/v1";

    // Camera
    public static final String Camera = Api + "/camera";
    public static final String CameraId = "/{cameraUuid}";
    public static final String CameraIdToken = CameraId + "/token";
    public static final String Cameras = Api + "/cameras";

    // OpenVidu
    public static final String OpenVidu = Api + "/ov";
    public static final String OpenViduWebHook = OpenVidu + "/webhooks";
}
