package use_case.event.edit_event;

import entity.event.EventInterface;

/**
 * The DAO for the Edit Event Use Case.
 */
public interface EditEventDataAccessInterface {

    /**
     * Return if an event with the given title exists.
     * @param title the title to look for
     * @return true if an event with the given title exists, or false otherwise
     */
    boolean existsByTitle(String title);

    /**
     * Edit an event of the given title with new information from a new event.
     * @param title the title of the event to edit
     * @param event the event to gather attributes from
     */
    void editEvent(String title, EventInterface event);
}
