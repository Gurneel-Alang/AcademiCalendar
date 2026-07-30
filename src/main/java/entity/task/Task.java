package entity.task;

/**
 * A checklist task belonging to an event.
 */
public class Task {

    private final String id;
    private final String eventId;
    private final String description;
    private boolean completed;

    public Task(
            String id,
            String eventId,
            String description,
            boolean completed
    ) {
        this.id = id;
        this.eventId = eventId;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        // example relationship: task.getEventId().equals(statsExam.getId())
        return eventId;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    public void markIncomplete() {
        completed = false;
    }
}