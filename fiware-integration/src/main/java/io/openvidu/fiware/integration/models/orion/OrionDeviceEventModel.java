/*
 * (C) Copyright 2018 Kurento (http://kurento.org/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package io.openvidu.fiware.integration.models.orion;


import io.openvidu.fiware.integration.models.api.ApiCameraModel;

import java.util.StringJoiner;


/**
 * A device event for orion.
 */
public class OrionDeviceEventModel {
    private String timestamp;
    private String filterType;
    private String eventType;
    private Object data;
    private ApiCameraModel camera;

    public OrionDeviceEventModel(String timestamp, String filterType, String eventType, Object data,
            ApiCameraModel camera) {
        this.timestamp = timestamp;
        this.filterType = filterType;
        this.eventType = eventType;
        this.data = data;
        this.camera = camera;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiCameraModel getCamera() {
        return camera;
    }

    public void setCamera(ApiCameraModel camera) {
        this.camera = camera;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", OrionDeviceEventModel.class.getSimpleName() + "[", "]")
                .add("timestamp='" + timestamp + "'").add("filterType='" + filterType + "'")
                .add("eventType='" + eventType + "'").add("data='" + data + "'").add("camera=" + camera).toString();
    }
}
