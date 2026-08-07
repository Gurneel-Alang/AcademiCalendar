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
        if (taskId == null || taskId.isBlank()){
            throw new IllegalArgumentException("Task ID cannot be blank.");
        }

        if (description ==  null || description.isBlank()){
            throw new IllegalArgumentException("Task description cannot be blank.");
        }
        this.taskId = taskId;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return taskId;
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
        return taskId.equals(task.taskId);
    }

    /**
     * generates hash code for Task based on its id
     * matters bc equals method above says two tasks are equal when IDs match
     */
    @Override
    public int hashCode() {
        return Objects.hash(taskId);
    }
}