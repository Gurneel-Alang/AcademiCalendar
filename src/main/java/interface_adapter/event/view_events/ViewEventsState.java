package interface_adapter.event.view_events;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the "view events for a given date" view model.
 */
public class ViewEventsState {

    private List<EventSummary> events = new ArrayList<>();
    private String errorMessage;

    public List<EventSummary> getEvents() {
        return List.copyOf(events);
    }

    public void setEvents(List<EventSummary> events) {
        this.events = new ArrayList<>(events);
    }

    /**
     * Return the error message.
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the error message.
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
