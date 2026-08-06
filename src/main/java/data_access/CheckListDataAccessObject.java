package data_access;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import entity.task.Task;
import use_case.task.TaskDataAccessInterface;

/**
 * In-memory storage for checklist tasks.
 * Can later be replaced by file or database storage without changing interactors
 */
public class CheckListDataAccessObject implements TaskDataAccessInterface {

    private final Map<String, Task> tasksById = new LinkedHashMap<>(); //linked hashmap preserves insertion order, so tasks appear in the order the user added them.

    @Override
    public void save(Task task) {
        tasksById.put(task.getId(), task);
    }

    @Override
    public Task getById(String taskId) {
        final Task task = tasksById.get(taskId);

        if (task == null) {
            throw new NoSuchElementException(
                    "No task exists with ID: " + taskId
            );
        }
        return task;
    }

    @Override
    public List<Task> getTasksForEvent(String eventId) {
        final List<Task> result = new ArrayList<>();

        for (Task task : tasksById.values()) {
            if (task.getEventId().equals(eventId)) {
                result.add(task);
            }
        }
        return result;
    }

    @Override
    public void update(Task task){
        if (!tasksById.containsKey(task.getId())) {
            throw new NoSuchElementException(
                    "Cannot update missing task: " + task.getId()
            );
        }
        tasksById.put(task.getId(), task);
    }
}
