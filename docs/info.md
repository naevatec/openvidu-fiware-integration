
# API Paths

## /api/v1/camera/

### POST

Create a new webrtc/rtsp camera.

#### REQUEST

```json5
{
    "cameraUuid": "string - the id of the camera to be able to register it again if it goes down. Can be null and it is generated.",
    "cameraAddress": "string - ip or url for rtsp",
    "protocol": "webrtc",
    "filter": "string - name of the filter",
    "events": "string[] - name of the each event to handle",
}
```

#### RESPONSE

- 200 - OK. Send `Camera` model.
- 4XX - The camera uuid is already registered and is active.
- 4XX - The filter is not available.
- 4XX - Bad request - ::Wrong parameter message::

## /api/v1/camera/{cameraUuid}/

### GET

Gets the information of specified camera.

#### RESPONSE

- 200 - OK. Send `Camera` model.
- 4XX - The camera uuid does not exist.

### POST

Updates the specified camera.

#### REQUEST

```json5
{
    "address": "string - ip or url for rstp",
    "filter": "string - name of the filter",
    "events": "string[] - name of the each event to handle",
}
```

#### RESPONSE

- 200 - OK. Send `Camera` model.
- 4XX - The camera uuid does not exist.
- 4XX - The filter is not available.
- 4XX - Bad request - ::Wrong parameter message::

### DELETE

Removes the specified camera.

#### RESPONSE

- 200 - OK
- 4XX - The camera uuid does not exist.

## /api/v1/cameras/

### GET

Gets the information of all available registered cameras.

#### RESPONSE

- 200 - OK

```json5
{
    "cameras": "List<Camera> - the list of camera models",
}
```

## /api/v1/camera/{cameraUuid}/token/

### GET

Gets a subscriber token for the camera.

#### RESPONSE

- 200 - OK

```json5
{
    "token": "String - The token to subscribe to the specified camera",
}
```

- 404: The camera does not exist.

# Webhooks paths

## /api/v1/ov/webhook/

Receives all the necessary events to handle the status of the camera.

### REQUEST

Any of:

- WebrtcConnectionCreated
- WebrtcConnectionDestroyed

#### RESPONSE

- 200 - OK
