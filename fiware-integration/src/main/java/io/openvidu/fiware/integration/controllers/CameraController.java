package io.openvidu.fiware.integration.controllers;

import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.fiware.integration.models.api.requests.ApiCameraRequest;
import io.openvidu.fiware.integration.services.CameraService;
import io.openvidu.fiware.integration.utils.ApiPaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
public class CameraController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CameraService cameraService;

    @PostMapping(ApiPaths.Camera)
    @ResponseStatus(value = HttpStatus.OK)
    public ApiCameraModel createCamera(@RequestBody ApiCameraRequest request, HttpServletRequest httpServletRequest) {
        log.debug("INIT [POST  ] createCamera: " + request);
        if ("".equals(request.getCameraUuid())) {
            request.setCameraUuid(null);
        }

        if ("webrtc".equals(request.getProtocol())) {
            request.setCameraAddress(httpServletRequest.getRemoteAddr());
        }

        ApiCameraModel result = cameraService.createCamera(request);
        log.debug("END  [POST  ] createCamera: " + request);
        return result;
    }

    @GetMapping(ApiPaths.CameraId)
    @ResponseStatus(value = HttpStatus.OK)
    public ApiCameraModel getCamera(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("INIT [GET   ] getCamera: " + cameraUuid);

        ApiCameraModel result = cameraService.getCamera(cameraUuid);

        log.debug("END  [GET   ] getCamera: " + cameraUuid);
        return result;
    }

    @GetMapping(ApiPaths.CameraIdToken)
    @ResponseStatus(value = HttpStatus.OK)
    public String getCameraTokenForConnection(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("INIT [GET   ] getCameraTokenForConnection: " + cameraUuid);

        String result = cameraService.getCameraToken(cameraUuid);

        log.debug("END  [GET   ] getCameraTokenForConnection: " + cameraUuid);
        return result;
    }

    @DeleteMapping(ApiPaths.CameraId)
    @ResponseStatus(value = HttpStatus.OK)
    public void removeCamera(@PathVariable("cameraUuid") String cameraUuid) {
        log.debug("INIT [DELETE] removeCamera: " + cameraUuid);

        cameraService.deleteCamera(cameraUuid);

        log.debug("END  [DELETE] removeCamera: " + cameraUuid);
    }

    @PostMapping(ApiPaths.CameraId)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCamera(@PathVariable("cameraUuid") String cameraUuid, @RequestBody ApiCameraRequest request) {
        log.debug("INIT [POST  ] updateCamera: " + cameraUuid + ", request: " + request);

        cameraService.updateCamera(cameraUuid, request);

        log.debug("END  [POST  ] updateCamera: " + cameraUuid + ", request: " + request);
    }

    @GetMapping(ApiPaths.Cameras)
    @ResponseStatus(value = HttpStatus.OK)
    public List<ApiCameraModel> getAllCameras() {
        log.debug("INIT [GET   ] getAllCameras");

        List<ApiCameraModel> result = cameraService.getAllCameras();

        log.debug("END  [GET   ] getAllCameras");
        return result;
    }
}
