package use_case.task.load_checklist;

import java.util.List;

public class LoadChecklistOutputData {
    private final String eventId;
    private final List<TaskOutputData> tasks;

    public LoadChecklistOutputData(String eventId, List<TaskOutputData> tasks) {
        this.eventId = eventId;
        this.tasks = List.copyOf(tasks);
    }

    public String getEventId() {
        return eventId;
    }

    public List<TaskOutputData> getTasks() {
        return tasks;
    }
}
