package io.openvidu.fiware.integration.services;

import io.openvidu.fiware.integration.config.OpenViduConfig;
import io.openvidu.fiware.integration.models.OpenViduSession;
import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.fiware.integration.models.api.requests.ApiCameraRequest;
import io.openvidu.fiware.integration.utils.Utils;
import io.openvidu.java.client.OpenViduRole;
import io.openvidu.java.client.Session;
import io.openvidu.java.client.TokenOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class CameraService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OpenViduConfig openViduConfig;

    /**
     * Creates a new camera.
     */
    public ApiCameraModel createCamera(ApiCameraRequest request) {
        // Auto-generate the uuid if it is not present.
        String cameraUuid = request.getCameraUuid();
        if (cameraUuid == null) {
            try {
                MessageDigest salt = MessageDigest.getInstance("SHA-256");
                salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
                cameraUuid = Utils.bytesToHex(salt.digest()).substring(0, 16);
            } catch (Exception e) {
                log.error("Error creating an uuid for the camera", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error creating an uuid for the camera");
            }
        }

        // The session to connect to.
        String sessionName;
        try {
            sessionName = Utils.cameraUuidToSession(cameraUuid);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error obtaining the name of the session", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obtaining the name of the session");
        }

        OpenViduSession session = openViduConfig.getSessionMap().get(sessionName);

        if (session != null && session.getCamera().isActive()) {
            // Already created session with an active camera.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There is already a camera register by the uuid: " + cameraUuid);
        }

        // Build tokenOptions object with the serverData and the role
        TokenOptions tokenOptions = new TokenOptions.Builder().role(OpenViduRole.PUBLISHER).build();

        // Create the session if it does not exist.
        if (session == null) {
            try {
                // Create a new OpenVidu Session.
                Session ovSession = openViduConfig.getOpenVidu().createSession();
                String token = ovSession.generateToken(tokenOptions);

                ApiCameraModel model = new ApiCameraModel();
                model.setCameraUuid(cameraUuid);
                model.setAddress(openViduConfig.getUrl());
                model.setCameraAddress(request.getCameraAddress());
                model.setOVToken(token);
                model.setProtocol(request.getProtocol());

                session = new OpenViduSession(sessionName, token, ovSession, model); // TODO change active to false.
                openViduConfig.getSessionMap().put(sessionName, session);
                // TODO update orion.
            } catch (Exception e) {
                log.error("Error creating a new OpenVidu session", e);
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Error creating a new OpenVidu session");
            }
        }

        return session.getCamera();
    }

    /**
     * Gets a camera.
     */
    public ApiCameraModel getCamera(String cameraUuid) {
        // The session to connect to.
        String sessionName;
        try {
            sessionName = Utils.cameraUuidToSession(cameraUuid);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error obtaining the name of the session for: " + cameraUuid, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obtaining the name of the session for: " + cameraUuid);
        }

        OpenViduSession session = openViduConfig.getSessionMap().get(sessionName);

        if (session == null) {
            // Undefined session.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }

        return session.getCamera();
    }

    /**
     * Creates a new camera.
     */
    public void deleteCamera(String cameraUuid) {
        // The session to connect to.
        String sessionName;
        try {
            sessionName = Utils.cameraUuidToSession(cameraUuid);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error obtaining the name of the session for: " + cameraUuid, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obtaining the name of the session for: " + cameraUuid);
        }

        OpenViduSession session = openViduConfig.getSessionMap().get(sessionName);

        if (session == null) {
            // Undefined session.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }

        // TODO update orion.
        openViduConfig.getSessionMap().remove(sessionName);
    }

    /**
     * Gets a new camera token to visualize its video.
     */
    public String getCameraToken(String cameraUuid) {
        // The session to connect to.
        String sessionName;
        try {
            sessionName = Utils.cameraUuidToSession(cameraUuid);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error obtaining the name of the session for: " + cameraUuid, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obtaining the name of the session for: " + cameraUuid);
        }

        OpenViduSession session = openViduConfig.getSessionMap().get(sessionName);

        if (session == null) {
            // Undefined session.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }

        if (!session.getCamera().isActive()) {
            // Already created session with an active camera.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera is inactive. For uuid: " + cameraUuid);
        }

        // Build tokenOptions object with the serverData and the role
        TokenOptions tokenOptions = new TokenOptions.Builder().role(OpenViduRole.SUBSCRIBER).build();

        // Create a new OpenVidu token.
        Session ovSession = session.getSession();

        try {
            return ovSession.generateToken(tokenOptions);
        } catch (Exception e) {
            log.error("Error obtaining a new subscriber token for: " + cameraUuid, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obtaining a new subscriber token for: " + cameraUuid);
        }
    }


    /**
     * Updates the specified camera.
     */
    public void updateCamera(String cameraUuid, ApiCameraRequest request) {
        // The session to connect to.
        String sessionName;
        try {
            sessionName = Utils.cameraUuidToSession(cameraUuid);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error obtaining the name of the session for: " + cameraUuid, e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error obtaining the name of the session for: " + cameraUuid);
        }

        OpenViduSession session = openViduConfig.getSessionMap().get(sessionName);

        if (session == null) {
            // Undefined session.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }


        // TODO update OV
        // TODO update ORION

        session.getCamera().setFilter(request.getFilter());
        session.getCamera().setEvents(request.getEvents());
    }

    /**
     * Gets all available cameras.
     */
    public List<ApiCameraModel> getAllCameras() {
        Map<String, OpenViduSession> cameras = openViduConfig.getSessionMap();
        List<ApiCameraModel> results = new ArrayList<>();

        for (OpenViduSession ovs : cameras.values()) {
            results.add(ovs.getCamera());
        }

        return results;
    }
}
