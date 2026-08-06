package use_case.task.toggle_task;

public class ToggleTaskInputData {
    private final String taskId;

    public ToggleTaskInputData(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }
}
