package use_case.add_event;

import entity.event.EventInterface;

/**
 * DAO for the Add Event Use Case.
 */
public interface AddEventDataAccessInterface {

    /**
     * Return if an event with the given title exists.
     * @param title the title to look for
     * @return true if an event with the given title exists, or false otherwise
     */
    boolean existsByTitle(String title);

    /**
     * Add an event.
     * @param event the event to add
     */
    void addEvent(EventInterface event);
}
