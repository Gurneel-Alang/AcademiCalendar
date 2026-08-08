package entity.task;

import java.util.UUID;
import java.time.LocalDate;

/**
 * Default implementation of TaskFactory.
 */
public class CommonTaskFactory implements TaskFactory {

    @Override
    public Task create(String description, LocalDate dueDate) {
        final String taskId = UUID.randomUUID().toString();

        return new Task(
                taskId,
                description,
                dueDate,
                false
        );
    }
}
