package use_case.task.create_task;

public class CreateTaskOutputData {
    private final String taskId;
    private final String eventId;
    private final String description;
    private final boolean completed;

    public CreateTaskOutputData(
            String taskId,
            String eventId,
            String description,
            boolean completed
    ) {
        this.taskId = taskId;
        this.eventId = eventId;
        this.description = description;
        this.completed = completed;
    }

    public String getTaskId(){
        return taskId;
    }

    public String getEventId(){
        return eventId;
    }

    public String getDescription(){
        return description;
    }

    public boolean isCompleted(){
        return completed;
    }
}
