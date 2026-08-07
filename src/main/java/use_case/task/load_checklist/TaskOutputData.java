package use_case.task.load_checklist;

public class TaskOutputData {
    private final String taskId;
    private final String description;
    private final boolean completed;

    public TaskOutputData(String taskId, String description, boolean completed) {
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }

    public String getId(){
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
