package interface_adapter.checklist;

import java.util.ArrayList;
import java.util.List;

public class ChecklistState {
    private String eventId = "";
    private List<TaskState> tasks = new ArrayList<>();
    private String errorMessage = "";

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<TaskState> getTasks() {
        return List.copyOf(tasks);
    }

    public void setTasks(List<TaskState> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void addTask(TaskState task) {
        tasks.add(task);
    }

    public void replaceTask(TaskState replacement) {
        for (int index = 0; index < tasks.size(); index++) {
            if (tasks.get(index).getId().equals(replacement.getId())) {
                tasks.set(index, replacement);
                return;
            }
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
