package entity.task;

/**
 * Creates Task entities.
 */
public interface TaskFactory {
    Task create(String description);
}