package interface_adapter.create_task;

/**
 * UI representation of one task.
 */
public class TaskState {

    private final String taskId;
    private final String description;
    private boolean completed;

    public TaskState(
            String taskId,
            String description,
            boolean completed
    ) {
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}