package use_case.task.create_task;

public class CreateTaskOutputData {
    private final String taskId;
    private final String description;
    private final boolean completed;

    public CreateTaskOutputData(
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
}
