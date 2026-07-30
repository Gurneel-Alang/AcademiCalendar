package use_case.event_use_case.delete_event;

/**
 * DAO for the Edit Event Use Case.
 */
public interface DeleteEventDataAccessInterface {

    /**
     * Delete an event of the given title
     * @param title the title of the event to delete
     */
    void deleteEvent(String title);
}
