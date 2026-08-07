package entity.task;

/**
 * Creates Task entities.
 */
public interface TaskFactory {

    /**
     * Return a new task.
     * @param eventId the task's event ID
     * @param description the task's description
     * @return a new task
     */
    Task create(String eventId, String description);
}
