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
import io.openvidu.fiware.integration.models.orion.MediaEvent;
import io.openvidu.fiware.integration.models.orion.OrionDeviceEventModel;

import java.text.SimpleDateFormat;

/**
 * Extension of the {@link DeviceOrionPublisher} for accepting a custom
 * {@link OrionDeviceEventModel}
 */
public class OrionDeviceEventPublisher extends MediaEventOrionPublisher<OrionDeviceEventModel> {

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");

    public OrionDeviceEventPublisher(OrionConnectorConfiguration config) {
        super(config);
    }

    /**
     * Uses the {@link OrionDeviceEventModel} data to initialize a
     * {@link MediaEvent}
     *
     * @return Device the initialized {@link MediaEvent}
     */
    @Override
    public MediaEvent mapEntityToOrionEntity(OrionDeviceEventModel event) {
        MediaEvent orion_entity = new MediaEvent();

        orion_entity
                .setId(event.getFilterType() + "_" + event.getEventType() + "_" + event.getCamera().getCameraUuid() +
                        "_" + event.getTimestamp());
        orion_entity._getGsmaCommons().setDateCreated(event.getTimestamp());
        orion_entity.setData(event.getData());
        orion_entity.setDeviceSource(event.getCamera().getCameraUuid());
        orion_entity.setType(event.getFilterType());
        orion_entity.setEventType(event.getEventType());

        return orion_entity;
    }
}
