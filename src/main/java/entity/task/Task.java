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
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be blank.");
        }
        if (eventId == null || eventId.isBlank()) {
            throw new IllegalArgumentException("Event ID cannot be blank.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be blank.");
        }
        this.id = id;
        this.eventId = eventId;
        this.description = description;
        this.completed = completed;
    }

    /**
     * Return this task's ID.
     * @return this task's ID
     */
    public String getId() {
        return id;
    }

    /**
     * Return this task's event ID.
     * @return this task's event ID
     */
    public String getEventId() {
        // example relationship: task.getEventId().equals(statsExam.getId())
        return eventId;
    }

    /**
     * Return this task's description.
     * @return this task's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return whether this task is completed or not.
     * @return whether this task is completed or not
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * Switch this task's completion status.
     */
    public void toggleCompleted() {
        completed = !completed;
    }

    /**
     * Set this task's completion status.
     * @param completed the completion status
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other || !(other instanceof Task)) {
            return this == other;
        }

        final Task task = (Task) other;
        return id.equals(task.id);
    }

    /**
     * Generates hash code for a task based on its ID.
     * matters bc equals method above says two tasks are equal when IDs match
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
