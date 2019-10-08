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
import io.openvidu.fiware.integration.models.orion.Device;
import io.openvidu.fiware.integration.models.orion.DeviceJsonManager;

/**
 * Extension from {@link OrionReader} specialized for {@link Device}s
 */
public abstract class DeviceOrionReader<T extends Object> extends OrionReader<T, Device> {

    public DeviceOrionReader(OrionConnectorConfiguration config) {
        super(config, new DeviceJsonManager(), Device.class);
    }
}
