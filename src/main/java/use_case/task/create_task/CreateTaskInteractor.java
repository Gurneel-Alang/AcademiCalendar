package use_case.task.create_task;

import entity.task.Task;
import entity.task.TaskFactory;
import use_case.task.TaskDataAccessInterface;

public class CreateTaskInteractor implements CreateTaskInputBoundary {
    private final TaskDataAccessInterface taskDataAccessObject;
    private final CreateTaskOutputBoundary presenter;
    private final TaskFactory taskFactory;

    public CreateTaskInteractor(
            TaskDataAccessInterface taskDataAccessObject,
            CreateTaskOutputBoundary presenter,
            TaskFactory taskFactory
    ) {
        this.taskDataAccessObject = taskDataAccessObject;
        this.presenter = presenter;
        this.taskFactory = taskFactory;
    }

    @Override
    public void execute(CreateTaskInputData inputData) {
        final String description = inputData.getDescription();

        if (description == null || description.isBlank()) {
            presenter.prepareFailView("Task description cannot be empty.");
            return;
        }

        final Task task = taskFactory.create(description, inputData.getDueDate());
        taskDataAccessObject.save(task);

        final CreateTaskOutputData outputData = new CreateTaskOutputData(
                task.getId(),
                task.getDescription(),
                task.getDueDate(),
                task.isCompleted()
        );

        presenter.prepareSuccessView(outputData);
    }
}
