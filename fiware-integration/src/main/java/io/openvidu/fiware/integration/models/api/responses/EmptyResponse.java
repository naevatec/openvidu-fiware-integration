package io.openvidu.fiware.integration.models.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.StringJoiner;

/**
 * Response for every controller that has not returned value.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmptyResponse {
    public static EmptyResponse instance = new EmptyResponse();

    private EmptyResponse() {
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", EmptyResponse.class.getSimpleName() + "[", "]").toString();
    }
}
