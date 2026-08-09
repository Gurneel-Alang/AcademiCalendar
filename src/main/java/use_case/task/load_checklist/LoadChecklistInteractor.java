package use_case.task.load_checklist;

import java.util.List;
import entity.task.Task;
import interface_adapter.checklist.TaskState;
import use_case.task.TaskDataAccessInterface;

public class LoadChecklistInteractor implements LoadChecklistInputBoundary {
    private final TaskDataAccessInterface taskDataAccessObject;
    private final LoadChecklistOutputBoundary presenter;
    private final TaskSortStrategy sortStrategy;

    public LoadChecklistInteractor(TaskDataAccessInterface taskDataAccessObject,
            LoadChecklistOutputBoundary presenter, TaskSortStrategy sortStrategy) {
        this.taskDataAccessObject = taskDataAccessObject;
        this.presenter = presenter;
        this.sortStrategy = sortStrategy;
    }

    @Override
    public void execute() {
        final List<Task> tasks = sortStrategy.sort(taskDataAccessObject.getAll());
        presenter.prepareSuccessView(new LoadChecklistOutputData(tasks));
    }
}