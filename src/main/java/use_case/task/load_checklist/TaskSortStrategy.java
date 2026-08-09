package use_case.task.load_checklist;

import java.util.List;
import entity.task.Task;

public interface TaskSortStrategy {
    List<Task> sort(List<Task> tasks);
}
