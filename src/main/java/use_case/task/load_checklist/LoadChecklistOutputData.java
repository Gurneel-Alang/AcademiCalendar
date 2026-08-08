package use_case.task.load_checklist;

import java.util.List;
import entity.task.Task;

public class LoadChecklistOutputData {
    private final List<Task> tasks;

    public LoadChecklistOutputData(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}
