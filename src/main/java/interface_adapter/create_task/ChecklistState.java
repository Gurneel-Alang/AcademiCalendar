package interface_adapter.create_task;

import java.util.ArrayList;
import java.util.List;

/**
 * Complete state displayed by the checklist View.
 */
public class ChecklistState {

    private String eventId;
    private List<TaskState> tasks = new ArrayList<>();
    private String errorMessage;

    public ChecklistState() {
    }

    /**
     * Copy constructor.
     */
    public ChecklistState(ChecklistState other) {
        this.eventId = other.eventId;
        this.tasks = new ArrayList<>(other.tasks);
        this.errorMessage = other.errorMessage;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<TaskState> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void setTasks(List<TaskState> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}