package io.openvidu.fiware.integration.controllers;

import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.fiware.integration.models.api.requests.ApiCameraRequest;
import io.openvidu.fiware.integration.models.api.responses.EmptyResponse;
import io.openvidu.fiware.integration.services.CameraService;
import io.openvidu.fiware.integration.utils.ApiPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(ApiPaths.Camera)
public class CameraController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CameraService cameraService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.OK)
    public ApiCameraModel createCamera(@RequestBody ApiCameraRequest request, HttpServletRequest httpServletRequest) {
        log.debug("POST - createCamera: " + request);
        if ("".equals(request.getCameraUuid())) {
            request.setCameraUuid(null);
        }

        if ("webrtc".equals(request.getProtocol())) {
            request.setCameraAddress(httpServletRequest.getRemoteAddr());
        }

        return cameraService.createCamera(request);
    }

    @GetMapping(ApiPaths.CameraId)
    @ResponseStatus(value = HttpStatus.OK)
    public void getCamera(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("GET - getCamera: " + cameraUuid);
    }

    @GetMapping(ApiPaths.CameraIdToken)
    @ResponseStatus(value = HttpStatus.OK)
    public void getCameraTokenForConnection(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("GET - getCameraTokenForConnection: " + cameraUuid);
    }

    @DeleteMapping(ApiPaths.CameraId)
    @ResponseStatus(value = HttpStatus.OK)
    public EmptyResponse removeCamera(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("DELETE - removeCamera: " + cameraUuid);
        cameraService.deleteCamera(cameraUuid);
        return EmptyResponse.instance;
    }

    @PostMapping(ApiPaths.CameraId)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCamera(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("POST - createCamera: " + cameraUuid);
    }

    @GetMapping(ApiPaths.Cameras)
    @ResponseStatus(value = HttpStatus.OK)
    public void getAllCameras(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("GET - getAllCameras: " + cameraUuid);
    }
}
