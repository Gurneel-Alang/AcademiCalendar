package use_case.task.load_checklist;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import entity.task.Task;

public class DueDateSortStrategy implements TaskSortStrategy {
    @Override
    public List<Task> sort(List<Task> tasks){
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getDueDate,
                        Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }
}
