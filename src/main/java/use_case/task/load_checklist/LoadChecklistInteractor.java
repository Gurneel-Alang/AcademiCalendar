package use_case.task.load_checklist;

import java.util.List;
import entity.task.Task;
import use_case.task.TaskDataAccessInterface;

public class LoadChecklistInteractor implements LoadChecklistInputBoundary {
    private final TaskDataAccessInterface taskDataAccessObject;
    private final LoadChecklistOutputBoundary presenter;

    public LoadChecklistInteractor(TaskDataAccessInterface taskDataAccessObject,
            LoadChecklistOutputBoundary presenter) {
        this.taskDataAccessObject = taskDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute() {
        final List<Task> tasks = taskDataAccessObject.getAll();
        presenter.prepareSuccessView(new LoadChecklistOutputData(tasks));
    }
}