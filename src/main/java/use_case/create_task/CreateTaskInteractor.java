package use_case.create_task;

import entity.task.Task;
import entity.task.TaskFactory;

/**
 * Implements the business logic for creating a task.
 */
public class CreateTaskInteractor implements CreateTaskInputBoundary {

    private final AddTaskDataAccessInterface taskDataAccessObject;
    private final CreateTaskOutputBoundary presenter;
    private final TaskFactory taskFactory;

    public CreateTaskInteractor(
            AddTaskDataAccessInterface taskDataAccessObject,
            CreateTaskOutputBoundary presenter,
            TaskFactory taskFactory
    ) {
        this.taskDataAccessObject = taskDataAccessObject;
        this.presenter = presenter;
        this.taskFactory = taskFactory;
    }

    @Override
    public void execute(CreateTaskInputData inputData) {
        final String eventId = inputData.getEventId();
        final String description = inputData.getDescription();

        if (eventId == null || eventId.isBlank()) {
            presenter.prepareFailView("No event was selected.");
            return;
        }

        if (description == null || description.isBlank()) {
            presenter.prepareFailView("Task description cannot be empty.");
            return;
        }

        final String cleanedDescription = description.trim();

        final Task task = taskFactory.create(
                eventId,
                cleanedDescription
        );

        taskDataAccessObject.saveTask(task);

        final CreateTaskOutputData outputData =
                new CreateTaskOutputData(
                        task.getId(),
                        task.getEventId(),
                        task.getDescription(),
                        task.isCompleted()
                );

        presenter.prepareSuccessView(outputData);
    }
}