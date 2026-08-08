package interface_adapter.checklist;

import java.time.LocalDate;

public class TaskState {
    private final String taskId;
    private final String description;
    private final LocalDate dueDate;
    private final boolean completed;

    public TaskState(
            String taskId,
            String description,
            LocalDate dueDate,
            boolean completed
    ) {
        this.taskId = taskId;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = completed;
    }

    public String getId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate(){
        return dueDate;}

    public boolean isCompleted() {
        return completed;
    }
}
