package io.openvidu.fiware.integration.controllers;

import io.openvidu.fiware.integration.utils.ApiPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController(ApiPaths.OpenVidu)
public class OpenViduWebHookController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    //    @PostMapping(ApiPaths.OpenViduWebHook)
    //    @ResponseStatus(HttpStatus.OK)
    //    public void openViduWebHook(@RequestBody WebHookEventWrapper wrapper) {
    //        log.info("WebHook received from OpenVidu: " + wrapper);
    //    }

    @PostMapping(ApiPaths.OpenViduWebHook)
    @ResponseStatus(HttpStatus.OK)
    public void openViduWebHook() {
        log.info("WebHook received from OpenVidu");
    }
}
