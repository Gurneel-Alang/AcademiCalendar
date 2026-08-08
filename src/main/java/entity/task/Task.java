package entity.task;

import java.util.Objects;

/**
 * A task belonging to an event checklist.
 */
public class Task {
    private final String taskId;
    private final String description;
    private boolean completed;

    public Task(String taskId, String description, boolean completed) {
        if (taskId == null || taskId.isBlank()) {
            throw new IllegalArgumentException("Task ID cannot be blank.");
        }

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Task description cannot be blank.");
        }
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }

    /**
     * Return this task's ID.
     * @return this task's ID
     */
    public String getId() {
        return taskId;
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
        if (this == other) {
            return true;
        }
        if (!(other instanceof Task)) {
            return false;
        }

        final Task task = (Task) other;
        return taskId.equals(task.taskId);
    }

    /**
     * Generates hash code for a task based on its ID.
     * matters bc equals method above says two tasks are equal when IDs match
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}
