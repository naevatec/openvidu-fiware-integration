package io.openvidu.fiware.integration.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.openvidu.fiware.integration.utils.Consts;
import io.openvidu.java.client.Connection;
import io.openvidu.java.client.OpenVidu;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

public class OpenViduConfig {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private OpenVidu openVidu;
    private String url;
    private String filterUrl;
    private String secret;

    public OpenViduConfig(String url, String filterUrl, String secret) {
        this.url = url;
        this.filterUrl = filterUrl;
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

    public String getFilterUrl() {
        return filterUrl;
    }

    public void setFilterUrl(String filterUrl) {
        this.filterUrl = filterUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Session getSessionFromCameraId(String cameraUuid) {
        // Update contents.
        try {
            openVidu.fetch();
        } catch (Exception e) {
            log.error("Cannot update OpenVidu", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot update OpenVidu");
        }

        List<Session> sessions = openVidu.getActiveSessions();
        return sessions.stream().filter(s -> s.getSessionId().equals(cameraUuid)).findFirst().orElse(null);
    }

    public Connection getPublisherConnection(String cameraUuid) {
        // Get session.
        Session session = getSessionFromCameraId(cameraUuid);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The session for the camera uuid (" + cameraUuid + ") does not exist");
        }

        // If any time there's more than one publisher it must be filtered.
        List<Connection> connections = session.getActiveConnections();
        Collections.reverse(connections);
        Connection connection = null;

        JsonParser parser = new JsonParser();
        for (Connection cn : connections) {
            String clientData = cn.getClientData();
            if (cn.getClientData().isEmpty() || cn.getRole() != OpenViduRole.PUBLISHER) {
                continue;
            }

            try {
                JsonObject obj = parser.parse(clientData).getAsJsonObject();
                String prop = obj.get(Consts.OpenViduPublisherProperty).getAsString();


                if (Consts.OpenViduPublisherPropertyValue.equals(prop)) {
                    connection = cn;
                    break;
                }
            } catch (Throwable ignored) {
            }
        }

        return connection;
    }
}
