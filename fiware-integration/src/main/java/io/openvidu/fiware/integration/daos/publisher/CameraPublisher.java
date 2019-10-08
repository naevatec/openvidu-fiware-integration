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

package io.openvidu.fiware.integration.daos.publisher;

import io.openvidu.fiware.integration.config.OrionConnectorConfiguration;
import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.fiware.integration.models.orion.Device;
import io.openvidu.fiware.integration.utils.Consts;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of the {@link DeviceOrionPublisher} for accepting a custom
 * {@link ApiCameraModel}
 */
public class CameraPublisher extends DeviceOrionPublisher<ApiCameraModel> {

    public CameraPublisher(OrionConnectorConfiguration config) {
        super(config);
    }

    /**
     * Uses the {@link ApiCameraModel} data to initialize a {@link Device}
     *
     * @param cam the {@link ApiCameraModel} that should be mapped to a {@link Device}
     *
     * @return Device the initialized {@link Device}
     */
    @Override
    public Device mapEntityToOrionEntity(ApiCameraModel cam) {

        String[] supportedProtocol = {cam.getProtocol()};

        Device entity = new Device();
        List<String> controlledAsset = new ArrayList<>();
        if (cam.getFilter() != null && cam.getEvents().size() > 0) {
            String filter = cam.getFilter();
            for (String event : cam.getEvents()) {
                controlledAsset.add(filter + "." + event);
            }
        }

        entity.setType(Consts.OpenViduCameraOrionType);
        entity.setControlledAsset(controlledAsset.toArray(new String[0]));
        entity.setDateInstalled(cam.getCreationDate());
        entity.setDeviceState(cam.isActive() ? "active" : "inactive");
        entity._getDeviceCommons().setSupportedProtocol(supportedProtocol);
        entity._getGsmaCommons().setId(cam.getCameraUuid());
        entity._getGsmaCommons().setDateCreated(cam.getCreationDate());
        entity._getGsmaCommons().setDescription(cam.getDescription());
        entity._getGsmaCommons().setName(cam.getCameraUuid());

        String[] address = new String[] {cam.getCameraAddress(), cam.getAddress()};
        entity.setIpAddress(address);
        return entity;
    }
}
