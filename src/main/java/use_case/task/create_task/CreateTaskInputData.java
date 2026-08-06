package use_case.task.create_task;

public class CreateTaskInputData {
    private final String eventId;
    private final String description;

    public CreateTaskInputData(String eventId, String description) {
        this.eventId = eventId;
        this.description = description;
    }

    public String getEventId() {
        return eventId;
    }

    public String getDescription() {
        return description;
    }
}
