package io.openvidu.fiware.integration.services;

import io.openvidu.fiware.integration.config.OpenViduConfig;
import io.openvidu.fiware.integration.models.OpenViduSession;
import io.openvidu.fiware.integration.models.WebHookEventWrapper;
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

    /**
     * Activates the session of a camera.
     */
    public void activateSession(WebHookEventWrapper event) {
        String sessionName = event.getSessionId();
        OpenViduSession session = openViduConfig.getSessionByName(sessionName);

        if (session == null) {
            // Undefined session.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The session (" + sessionName + ") does not exist");
        }

        // Only act over the publisher.
        if ("OUTBOUND".equals(event.getConnection())) {
            log.info("Activating the camera: " + session.getCamera().getCameraUuid());
            session.getCamera().setActive(true);

            // TODO update orion.
        } else {
            log.debug("Ignoring web hook because it is not from the publisher. " + event);
        }
    }

    /**
     * Deactivates the session of a camera.
     */
    public void deactivateSession(WebHookEventWrapper event) {
        String sessionName = event.getSessionId();
        OpenViduSession session = openViduConfig.getSessionByName(sessionName);

        if (session == null) {
            // Undefined session.
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The session (" + sessionName + ") does not exist");
        }

        // Only act over the publisher.
        if ("OUTBOUND".equals(event.getConnection())) {
            log.info("Deactivating the camera: " + session.getCamera().getCameraUuid());
            session.getCamera().setActive(false);

            // TODO update orion.
        } else {
            log.debug("Ignoring web hook because it is not from the publisher. " + event);
        }
    }
}
