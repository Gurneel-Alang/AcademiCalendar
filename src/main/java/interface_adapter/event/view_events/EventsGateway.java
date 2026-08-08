package interface_adapter.event.view_events;

import entity.event.EventInterface;

import java.util.List;

/**
 * A read-only boundary for retrieving all stored events;
 */
public interface EventsGateway {

    /**
     * Return every event currently stored.
     * @return all events
     */
    List<EventInterface> getAllEvents();
}
