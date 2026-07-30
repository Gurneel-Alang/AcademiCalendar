package interface_adapter.create_task;

import use_case.create_task.CreateTaskInputBoundary;
import use_case.create_task.CreateTaskInputData;

/**
 * Converts raw View input into CreateTaskInputData.
 */
public class CreateTaskController {

    private final CreateTaskInputBoundary createTaskInteractor;

    public CreateTaskController(
            CreateTaskInputBoundary createTaskInteractor
    ) {
        this.createTaskInteractor = createTaskInteractor;
    }

    public void execute(String eventId, String taskDescription) {
        final CreateTaskInputData inputData =
                new CreateTaskInputData(
                        eventId,
                        taskDescription
                );

        createTaskInteractor.execute(inputData);
    }
}