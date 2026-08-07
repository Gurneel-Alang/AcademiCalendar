package use_case.event.delete_event;

/**
 * The DAO for the Delete Event Use Case.
 */
public interface DeleteEventDataAccessInterface {

    /**
     * Return if an event with the given title exists.
     * @param title the title to look for
     * @return true if an event with the given title exists, or false otherwise
     */
    boolean existsByTitle(String title);

    /**
     * Delete an event of the given title.
     * @param title the title of the event to delete
     */
    void deleteEvent(String title);
}
