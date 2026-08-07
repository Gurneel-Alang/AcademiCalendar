package use_case.task;

import java.util.List;
import entity.task.Task;

/**
 * this lets same DAO support all three use cases.
 */
public interface TaskDataAccessInterface {

    void save(Task task);
    Task getById(String taskId);
    void update(Task task);
    List<Task> getAll();
}
