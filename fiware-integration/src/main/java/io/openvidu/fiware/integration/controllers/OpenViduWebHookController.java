package io.openvidu.fiware.integration.controllers;

import io.openvidu.fiware.integration.models.WebHookEventWrapper;
import io.openvidu.fiware.integration.services.OpenViduWebHookService;
import io.openvidu.fiware.integration.utils.ApiPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class OpenViduWebHookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OpenViduWebHookService openViduWebHookService;

    @PostMapping(ApiPaths.OpenViduWebHook)
    @ResponseStatus(HttpStatus.OK)
    public void openViduWebHook(@RequestBody WebHookEventWrapper event) {
        String eventName = event.getEvent();

        log.debug("INIT [POST  ] WebHook " + eventName + " received from OpenVidu: " + event);

        if ("webrtcConnectionCreated".equals(eventName)) {
            openViduWebHookService.activateSession(event);
        } else if ("webrtcConnectionDestroyed".equals(eventName)) {
            openViduWebHookService.deactivateSession(event);
        } else {
            log.error("ERROR Unrecognized WebHook " + eventName + " received from OpenVidu: " + event);
        }

        log.debug("END  [POST  ] WebHook " + eventName + " received from OpenVidu: " + event);
    }
}
