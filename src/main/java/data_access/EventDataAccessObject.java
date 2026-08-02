package data_access;

import entity.event.EventInterface;
import use_case.event_use_case.add_event.AddEventDataAccessInterface;
import use_case.event_use_case.edit_event.EditEventDataAccessInterface;
import use_case.event_use_case.delete_event.DeleteEventDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * The DAO for events.
 */
public class EventDataAccessObject implements AddEventDataAccessInterface,
    EditEventDataAccessInterface, DeleteEventDataAccessInterface {

    private final Map<Integer, EventInterface> events = new HashMap<>();

    @Override
    public boolean existsByTitle(String title) {
        for (EventInterface event : events.values()) {
            if (title.equals(event.getTitle())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addEvent(EventInterface event) {
        events.put(Integer.valueOf(event.getId()), event);
    }

    /**
     * Helper method to gather the ID of an event given its title.
     * @param title the event title
     * @return the event's associated ID
     */
    public int getIdByTitle(String title) {
        for (Integer i : events.keySet()) {
            if (title.equals(events.get(i).getTitle())) {
                return i;
            }
        }
        throw new RuntimeException("No key found.");
    }

    @Override
    public void editEvent(String title, EventInterface other) {
        try {
            int id = getIdByTitle(title);
            events.put(id, other);
            other.setId(id);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteEvent(String title) {
        try {
            int id = getIdByTitle(title);
            events.remove(id);
        }
        catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
