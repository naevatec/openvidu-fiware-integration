package io.openvidu.fiware.integration.models;

import io.openvidu.fiware.integration.controllers.OpenViduWebHookController;
import io.openvidu.fiware.integration.events.openvidu.OpenViduWebrtcConnectionCreatedEvent;
import io.openvidu.fiware.integration.events.openvidu.OpenViduWebrtcConnectionDestroyedEvent;

import java.util.StringJoiner;

/**
 * Wrapper for all OpenVidu events that the {@link OpenViduWebHookController} receives.
 */
public class WebHookEventWrapper {
    private OpenViduWebrtcConnectionCreatedEvent webrtcConnectionCreated;
    private OpenViduWebrtcConnectionDestroyedEvent webrtcConnectionDestroyed;

    public WebHookEventWrapper(OpenViduWebrtcConnectionCreatedEvent webrtcConnectionCreated,
            OpenViduWebrtcConnectionDestroyedEvent webrtcConnectionDestroyed) {
        this.webrtcConnectionCreated = webrtcConnectionCreated;
        this.webrtcConnectionDestroyed = webrtcConnectionDestroyed;
    }

    public OpenViduWebrtcConnectionCreatedEvent getWebrtcConnectionCreated() {
        return webrtcConnectionCreated;
    }

    public void setWebrtcConnectionCreated(OpenViduWebrtcConnectionCreatedEvent webrtcConnectionCreated) {
        this.webrtcConnectionCreated = webrtcConnectionCreated;
    }

    public OpenViduWebrtcConnectionDestroyedEvent getWebrtcConnectionDestroyed() {
        return webrtcConnectionDestroyed;
    }

    public void setWebrtcConnectionDestroyed(OpenViduWebrtcConnectionDestroyedEvent webrtcConnectionDestroyed) {
        this.webrtcConnectionDestroyed = webrtcConnectionDestroyed;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WebHookEventWrapper.class.getSimpleName() + "[", "]")
                .add("webrtcConnectionCreated=" + webrtcConnectionCreated)
                .add("webrtcConnectionDestroyed=" + webrtcConnectionDestroyed).toString();
    }
}
