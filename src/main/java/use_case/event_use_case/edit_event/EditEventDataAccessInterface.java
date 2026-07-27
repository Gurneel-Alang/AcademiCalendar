package use_case.event_use_case.edit_event;

import entity.event.EventInterface;

/**
 * DAO for the Edit Event Use Case.
 */
public interface EditEventDataAccessInterface {

    /**
     * Edit an event of the given title with new information from a new event.
     * @param title the title of the event to edit
     * @param event the event to gather attributes from
     */
    void editEvent(String title, EventInterface event);
}
