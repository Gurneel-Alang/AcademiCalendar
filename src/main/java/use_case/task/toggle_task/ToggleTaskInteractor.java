package use_case.task.toggle_task;

import java.util.NoSuchElementException;

import entity.task.Task;
import use_case.task.TaskDataAccessInterface;

public class ToggleTaskInteractor implements ToggleTaskInputBoundary {
    private final TaskDataAccessInterface taskDataAccessObject;
    private final ToggleTaskOutputBoundary presenter;

    public ToggleTaskInteractor(
            TaskDataAccessInterface taskDataAccessObject,
            ToggleTaskOutputBoundary presenter
    ) {
        this.taskDataAccessObject = taskDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ToggleTaskInputData inputData) {
        try {
            final Task task =
                    taskDataAccessObject.getById(inputData.getTaskId());

            task.toggleCompleted();
            taskDataAccessObject.update(task);

            presenter.prepareSuccessView(
                    new ToggleTaskOutputData(
                            task.getId(),
                            task.isCompleted()
                    )
            );
        }
        catch (NoSuchElementException exception) {
            presenter.prepareFailView(exception.getMessage());
        }
    }
}

