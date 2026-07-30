package use_case.create_task;

import entity.task.Task;

import java.util.List;

/**
 * Data-access operations required by the Create Task use case.
 */
public interface AddTaskDataAccessInterface {
    void saveTask(Task task);
    List<Task> getTasksForEvent(String eventId);
}