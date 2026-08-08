package interface_adapter.checklist;

public class TaskState {
    private final String taskId;
    private final String description;
    private final boolean completed;

    public TaskState(
            String taskId,
            String description,
            boolean completed
    ) {
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
