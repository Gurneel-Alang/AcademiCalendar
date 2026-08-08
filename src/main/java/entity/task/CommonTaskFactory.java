package entity.task;

import java.util.UUID;

/**
 * Default implementation of TaskFactory.
 */
public class CommonTaskFactory implements TaskFactory {

    @Override
    public Task create(String description) {
        final String taskId = UUID.randomUUID().toString();

        return new Task(
                taskId,
                description,
                false
        );
    }
}
