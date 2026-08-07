package use_case.task.toggle_task;

public class ToggleTaskOutputData {
    private final String taskId;
    private final boolean completed;

    public ToggleTaskOutputData(String taskId, boolean completed){
        this.taskId = taskId;
        this.completed = completed;
    }

    public String getTaskId() {
        return taskId;
    }

    public boolean isCompleted() {
        return completed;
    }
}
