package use_case.task.load_checklist;

import java.util.List;

import entity.task.Task;
import use_case.task.TaskDataAccessInterface;

public class LoadChecklistInteractor implements LoadChecklistInputBoundary {
    private final TaskDataAccessInterface taskDataAccessObject;
    private final LoadChecklistOutputBoundary presenter;

    public LoadChecklistInteractor(
            TaskDataAccessInterface taskDataAccessObject,
            LoadChecklistOutputBoundary presenter
    ){
        this.taskDataAccessObject = taskDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoadChecklistInputData inputData) {
        if (inputData.getEventId() == null
                || inputData.getEventId().isBlank()) {
            presenter.prepareFailView("No event was selected.");
            return;
        }

        final List<TaskOutputData> taskOutputData =
                taskDataAccessObject
                        .getTasksForEvent(inputData.getEventId())
                        .stream()
                        .map(this::toOutputData)
                        .toList();

        presenter.prepareSuccessView(
                new LoadChecklistOutputData(
                        inputData.getEventId(),
                        taskOutputData
                )
        );
    }

    private TaskOutputData toOutputData(Task task) {
        return new TaskOutputData(
                task.getId(),
                task.getDescription(),
                task.isCompleted()
        );
    }
}
