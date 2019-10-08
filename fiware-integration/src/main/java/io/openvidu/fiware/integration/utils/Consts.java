package io.openvidu.fiware.integration.utils;

public class Consts {
    public static final String DefaultOwnUrl = "http://localhost:8080";

    public static final String DefaultOpenViduUrl = "https://localhost:4443";
    public static final String DefaultOpenViduFilterUrl = "http://localhost:9090";
    public static final String DefaultOpenViduSecret = "MY_SECRET";
    public static final String DefaultOrionUrl = "http://localhost:1026";

    public static final String OpenViduCameraOrionType = "OVCamera";

    // also change it in src/frontend/views/cameraCreated.vue
    public static final String OpenViduSignal = "switch-state";
    public static final String OpenViduUser = "OPENVIDUAPP";


    // also change the two below in src/frontend/views/cameraCreated.vue
    public static final String OpenViduPublisherProperty = "amITheCamera";
    public static final String OpenViduPublisherPropertyValue = "yes-I-am";
}
