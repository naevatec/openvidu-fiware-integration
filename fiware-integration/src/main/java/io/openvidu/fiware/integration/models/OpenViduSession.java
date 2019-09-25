package io.openvidu.fiware.integration.models;

import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.java.client.Session;

import java.util.StringJoiner;

/**
 * Contains all the related data of the sessions.
 */
public class OpenViduSession {
    private String name;
    private String publisherToken;
    private Session session;
    private ApiCameraModel camera;

    public OpenViduSession(String name, String publisherToken, Session session, ApiCameraModel camera) {
        this.name = name;
        this.publisherToken = publisherToken;
        this.session = session;
        this.camera = camera;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublisherToken() {
        return publisherToken;
    }

    public void setPublisherToken(String publisherToken) {
        this.publisherToken = publisherToken;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public ApiCameraModel getCamera() {
        return camera;
    }

    public void setCamera(ApiCameraModel camera) {
        this.camera = camera;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OpenViduSession.class.getSimpleName() + "[", "]").add("name='" + name + "'")
                .add("publisherToken='" + publisherToken + "'").add("session=" + session).add("camera=" + camera)
                .toString();
    }
}
