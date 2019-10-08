/*
 * Copyright 2018 Kurento (https://www.kurento.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openvidu.fiware.integration.daos.reader;

import io.openvidu.fiware.integration.config.OrionConnectorConfiguration;
import io.openvidu.fiware.integration.daos.OrionReader;
import io.openvidu.fiware.integration.models.orion.MediaEvent;
import io.openvidu.fiware.integration.models.orion.MediaEventJsonManager;

import java.util.List;

/**
 * Extension from {@link OrionReader} specialized for {@link MediaEvent}s
 */
public abstract class MediaEventOrionReader<T extends Object> extends OrionReader<T, MediaEvent> {

    public MediaEventOrionReader(OrionConnectorConfiguration config) {
        super(config, new MediaEventJsonManager(), MediaEvent.class);
    }

    /**
     * Get list of the mediaEvents of certain eventType not in the default reader.
     */
    public List<MediaEvent> readMediaEventListByEventType(String eventType) {

        // configure query
        String query = "eventType=='" + eventType + "'";

        return this.orionConnector.readEntityQueryList(MediaEvent.TYPE, query);
    }
}
