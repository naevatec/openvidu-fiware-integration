package io.openvidu.fiware.integration.services;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.openvidu.fiware.integration.config.OpenViduConfig;
import io.openvidu.fiware.integration.daos.publisher.CameraPublisher;
import io.openvidu.fiware.integration.daos.publisher.OrionDeviceEventPublisher;
import io.openvidu.fiware.integration.daos.reader.CameraReader;
import io.openvidu.fiware.integration.errors.OrionConnectorException;
import io.openvidu.fiware.integration.models.WebHookEventWrapper;
import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.fiware.integration.models.orion.OrionDeviceEventModel;
import io.openvidu.fiware.integration.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OpenViduWebHookService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OpenViduConfig openViduConfig;

    @Autowired
    CameraPublisher cameraPublisher;

    @Autowired
    CameraReader cameraReader;

    @Autowired
    OrionDeviceEventPublisher deviceEventPublisher;

    /**
     * Activates the session of a camera.
     */
    public void activateSession(WebHookEventWrapper event) {
        // Get camera.
        String cameraUuid = event.getSessionId();
        ApiCameraModel camera;
        try {
            camera = cameraReader.readObject(cameraUuid);
        } catch (OrionConnectorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }

        // Only act over the publisher.
        if ("OUTBOUND".equals(event.getConnection())) {
            camera.setActive(true);

            log.debug("Activating the camera in Orion: " + camera);

            cameraPublisher.update(camera);
        } else {
            log.debug("Ignoring web hook because it is not from the publisher. " + event);
        }
    }

    /**
     * Deactivates the session of a camera.
     */
    public void deactivateSession(WebHookEventWrapper event) {
        // Get camera.
        String cameraUuid = event.getSessionId();
        ApiCameraModel camera;
        try {
            camera = cameraReader.readObject(cameraUuid);
        } catch (OrionConnectorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }

        // Only act over the publisher.
        if ("OUTBOUND".equals(event.getConnection())) {
            camera.setActive(true);

            log.debug("Deactivating the camera in Orion: " + camera);

            cameraPublisher.update(camera);
        } else {
            log.debug("Ignoring web hook because it is not from the publisher. " + event);
        }
    }

    /**
     * Saves an event of a camera.
     */
    public void saveEvent(WebHookEventWrapper event) {
        // Get camera.
        String cameraUuid = event.getSessionId();
        ApiCameraModel camera;
        try {
            camera = cameraReader.readObject(cameraUuid);
        } catch (OrionConnectorException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The camera uuid (" + cameraUuid + ") does not exist");
        }

        log.debug("Save the camera event in Orion: " + camera);

        String timestamp = Utils.getFormattedCurrentDate();

        // TODO TEMPORAL - Remove when the input is a JSON.
        String newData = parseInputData(event.getData());
        JsonParser parser = new JsonParser();
        JsonObject newDataJson = parser.parse(newData).getAsJsonObject();
        log.debug("JULS newdata - " + newDataJson);

        OrionDeviceEventModel detectedEvent =
                new OrionDeviceEventModel(timestamp, event.getFilterType(), event.getEventType(), newDataJson, camera);


        deviceEventPublisher.publish(detectedEvent);
    }

    // TODO TEMPORAL - Remove when the input is a JSON.
    private String parseInputData(String input) {
        // Everything as String.
        String propsToStringPattern = "(\\w+)=(.+?)(,|})";
        String newData = input.replaceAll(propsToStringPattern, "\"$1\":\"$2\"$3");

        // Fix numbers.
        String fixNumbersPattern = ":\"(\\d+)\"";
        newData = newData.replaceAll(fixNumbersPattern, ":$1");

        // Fix arrays.
        String fixArraysPattern = ":\"(\\[.*\\])\"";
        newData = newData.replaceAll(fixArraysPattern, ":$1");

        return newData;
    }
}
