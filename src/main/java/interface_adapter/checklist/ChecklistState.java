package interface_adapter.checklist;

import java.util.ArrayList;
import java.util.List;

public class ChecklistState {
    private String eventId = "";
    private List<TaskState> tasks = new ArrayList<>();
    private String errorMessage = "";

    /**
     * Return the event ID.
     * @return the event Id
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Set the event ID.
     * @param eventId the event ID to set
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Return the tasks.
     * @return the tasks
     */
    public List<TaskState> getTasks() {
        return List.copyOf(tasks);
    }

    /**
     * Set the tasks.
     * @param tasks the tasks to set
     */
    public void setTasks(List<TaskState> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Add a task.
     * @param task the task to add
     */
    public void addTask(TaskState task) {
        tasks.add(task);
    }

    public void replaceTask(TaskState replacement) {
        for (int index = 0; index < tasks.size(); index++) {
            if (tasks.get(index).getId().equals(replacement.getId())) {
                tasks.set(index, replacement);
                break;
            }
        }
    }

    /**
     * Return the error message.
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Set the error message.
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
