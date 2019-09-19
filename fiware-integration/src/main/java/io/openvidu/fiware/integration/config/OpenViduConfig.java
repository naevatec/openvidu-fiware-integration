package io.openvidu.fiware.integration.config;

import io.openvidu.fiware.integration.models.OpenViduSession;
import io.openvidu.java.client.OpenVidu;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OpenViduConfig {
    private OpenVidu openVidu;
    private String url;
    private String secret;

    private Map<String, OpenViduSession> sessionMap = new ConcurrentHashMap<>();

    public OpenViduConfig(String url, String secret) {
        this.url = url;
        this.secret = secret;
        this.openVidu = new OpenVidu(url, secret);
    }

    public OpenVidu getOpenVidu() {
        return openVidu;
    }

    public void setOpenVidu(OpenVidu openVidu) {
        this.openVidu = openVidu;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Map<String, OpenViduSession> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, OpenViduSession> sessionMap) {
        this.sessionMap = sessionMap;
    }
}
