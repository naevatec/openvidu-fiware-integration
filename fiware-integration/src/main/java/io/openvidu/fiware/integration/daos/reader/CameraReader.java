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

package io.openvidu.fiware.integration.daos.reader;

import io.openvidu.fiware.integration.config.OrionConnectorConfiguration;
import io.openvidu.fiware.integration.models.api.ApiCameraModel;
import io.openvidu.fiware.integration.models.orion.Device;
import io.openvidu.fiware.integration.models.orion.OrionCameraModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Extension of the {@link DeviceOrionReader} for reading a custom
 * {@link OrionCameraModel} from Orion
 */
public class CameraReader extends DeviceOrionReader<ApiCameraModel> {

    public CameraReader(OrionConnectorConfiguration config) {
        super(config);
    }

    /**
     * Initalises a {@link OrionCameraModel} data from a {@link Device}
     *
     * @param entity the {@link Device} that should be mapped to a {@link OrionCameraModel}
     *
     * @return Camera the initialized {@link OrionCameraModel}
     */
    @Override
    public ApiCameraModel mapOrionEntityToEntity(Device entity) {
        ApiCameraModel cam = new ApiCameraModel();
        cam.setCreationDate(entity.getDateInstalled());
        cam.setActive("active".equals(entity.getDeviceState()));
        cam.setProtocol(entity._getDeviceCommons().getSupportedProtocol()[0]);
        cam.setCameraUuid(entity._getGsmaCommons().getId());
        cam.setDescription(entity._getGsmaCommons().getDescription());
        cam.setCameraAddress(entity.getIpAddress()[0]);
        cam.setAddress(entity.getIpAddress()[1]);

        List<String> events = new ArrayList<>();
        String filter = null;

        for (String event : entity.getControlledAsset()) {
            String[] filterEvent = event.split("\\.");

            filter = filterEvent[0]; // Because cameras can only have one filter.
            events.add(filterEvent[1]);
        }

        cam.setFilter(filter);
        cam.setEvents(events);

        return cam;
    }

}
