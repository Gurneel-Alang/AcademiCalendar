package entity.task;

import java.util.Objects;

/**
 * A task belonging to an event checklist.
 */
public class Task {
    private final String id;
    private final String eventId;
    private final String description;
    private boolean completed;

    public Task(String id, String eventId, String description, boolean completed) {
        if (id == null || id.isBlank()){
            throw new IllegalArgumentException("Task ID cannot be blank.");
        }
        if (eventId == null || eventId.isBlank()){
            throw new IllegalArgumentException("Event ID cannot be blank.");
        }
        if (description ==  null || description.isBlank()){
            throw new IllegalArgumentException("Task description cannot be blank.");
        }
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

    public void toggleCompleted() {
        completed = !completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }

        final Task task = (Task) other;
        return id.equals(task.id);
    }

    /**
     * generates hash code for Task based on its id
     * matters bc equals method above says two tasks are equal when IDs match
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}