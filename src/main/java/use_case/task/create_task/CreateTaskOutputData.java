package use_case.task.create_task;

import java.time.LocalDate;

public class CreateTaskOutputData {
    private final String taskId;
    private final LocalDate dueDate;
    private final String description;
    private final boolean completed;

    public CreateTaskOutputData(
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

    public String getTaskId() {
        return taskId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate(){
        return dueDate;}

    public boolean isCompleted(){
        return completed;
    }
}
