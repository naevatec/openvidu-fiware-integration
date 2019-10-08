package io.openvidu.fiware.integration.models.api.requests;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.StringJoiner;

/**
 * Model for a Camera in the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateCameraRequest {
    @NotEmpty
    private String filter;
    private List<String> events;
    private String description;
    private Boolean active;

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public List<String> getEvents() {
        return events;
    }

    public void setEvents(List<String> events) {
        this.events = events;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UpdateCameraRequest.class.getSimpleName() + "[", "]")
                .add("filter='" + filter + "'").add("events=" + events).add("description='" + description + "'")
                .add("active=" + active).toString();
    }
}
