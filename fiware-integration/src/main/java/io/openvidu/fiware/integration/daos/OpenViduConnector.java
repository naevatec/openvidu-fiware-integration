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

package io.openvidu.fiware.integration.daos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.openvidu.fiware.integration.config.OpenViduConfig;
import io.openvidu.fiware.integration.errors.OpenViduConnectorException;
import io.openvidu.fiware.integration.models.openvidu.requests.OpenViduFilterRequest;
import io.openvidu.fiware.integration.models.openvidu.requests.OpenViduSignalRequest;
import org.apache.http.client.fluent.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

/**
 * Connector to the OpenVidu api.
 */
@Service
public class OpenViduConnector {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static final String APPLY_FILTER_PATH = "/apply-filter";
    private static final String REMOVE_FILTER_PATH = "/remove-filter";
    private static final String EXEC_FILTER_METHOD_PATH = "/exec-filter-method";
    private static final String ADD_FILTER_EVENT_LISTENER_PATH = "/add-filter-event-listener";
    private static final String REMOVE_FILTER_EVENT_LISTENER_PATH = "/remove-filter-event-listener";
    private static final String SIGNAL_PATH = "/api/signal";

    private OpenViduConfig config;
    private Gson gson;

    /**
     * OpenVidu connector constructor.
     */
    public OpenViduConnector(OpenViduConfig config) {
        this.config = config;
        this.gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create();
    }

    /**
     * Applies a filter to a specific stream.
     */
    public void applyFilter(String sessionId, String streamId, String filter) {
        OpenViduFilterRequest request = new OpenViduFilterRequest();
        request.setSessionId(sessionId);
        request.setStreamId(streamId);
        request.setType(filter);
        request.setOptions("{}"); // Empty JSON object.

        sendPostRequest(request, config.getFilterUrl() + APPLY_FILTER_PATH);
    }

    /**
     * Removes a filter of a specific stream.
     */
    public void removeFilter(String sessionId, String streamId) {
        OpenViduFilterRequest request = new OpenViduFilterRequest();
        request.setSessionId(sessionId);
        request.setStreamId(streamId);

        sendPostRequest(request, config.getFilterUrl() + REMOVE_FILTER_PATH);
    }

    /**
     * Adds a filter event listener.
     */
    public void addEventListener(String sessionId, String streamId, String event) {
        OpenViduFilterRequest request = new OpenViduFilterRequest();
        request.setSessionId(sessionId);
        request.setStreamId(streamId);
        request.setEventType(event);

        sendPostRequest(request, config.getFilterUrl() + ADD_FILTER_EVENT_LISTENER_PATH);
    }

    /**
     * Removes a filter event listener.
     */
    public void removeEventListener(String sessionId, String streamId, String event) {
        OpenViduFilterRequest request = new OpenViduFilterRequest();
        request.setSessionId(sessionId);
        request.setStreamId(streamId);
        request.setEventType(event);

        sendPostRequest(request, config.getFilterUrl() + REMOVE_FILTER_EVENT_LISTENER_PATH);
    }

    /**
     * Sends a signal to the specified session.
     */
    public void sendSignal(String sessionId, String type, String data, List<String> to) {
        OpenViduSignalRequest request = new OpenViduSignalRequest();
        request.setSession(sessionId);
        request.setType(type);
        request.setData(data);
        request.setTo(to);

        sendPostRequest(request, config.getUrl() + SIGNAL_PATH);
    }

    private void sendPostRequest(Object request, String uri) {
        Request req = Request.Post(uri).addHeader("Accept", APPLICATION_JSON.getMimeType()).socketTimeout(5000);

        if (request != null) {
            String jsonEntity = gson.toJson(request);
            req.bodyString(jsonEntity, APPLICATION_JSON).connectTimeout(5000);
        }

        try {
            req.execute();
        } catch (IOException e) {
            throw new OpenViduConnectorException("Could not execute HTTP request", e);
        }
    }
}
