package entity.task;

import java.time.LocalDate;

/**
 * Creates Task entities.
 */
public interface TaskFactory {
    Task create(String description, LocalDate dueDate);
}