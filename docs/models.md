
# API Models

## Camera

```json5
{
    "cameraUuid": "string - the id of the camera to be able to register it again if it goes down",
    "cameraAddress": "string - ip or url for rtsp or webrtc",
    "protocol": "webrtc",
    "address": "string - ip of the camera",
    "filter": "string - name of the filter",
    "events": "string[] - name of the each event to handle",
    "openvidu": {
        "address": "string - the OpenVidu address to be able to connect with it",
        "sessionId": "string - the OpenVidu session id",
    },
    "creationDate": "date - the date when the camera was created",
    "updateDate": "date - the last date when the camera was updated",
}
```

## OrionCamera

```json5
{
    // Orion
    "id": "string - the id for Orion",
    "type": "string - the type for Orion",
    "state": "string[NEW,CONFIGURED,PROCESSING,STOP,DISABLED] - the status for Orion",

    // Camera information
    "cameraUuid": "string - the unique id of the camera for the specified instance",
    "instanceId": "string - the id of the app instance",
    "protocol": "webrtc",
    "address": "string - ip of the camera",
    "filter": "string - name of the filter",
    "events": "string[] - name of the each event to handle",
    "creationDate": "date - the date when the camera was created",
    "updateDate": "date - the last date when the camera was updated",

    // Openvidu information
    "openvidu": {
        "address": "string - the OpenVidu address to be able to connect with it",
        "sessionId": "string - the OpenVidu session id",
    }
}
```
