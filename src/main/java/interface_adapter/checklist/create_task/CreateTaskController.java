package interface_adapter.checklist.create_task;

import use_case.task.create_task.CreateTaskInputBoundary;
import use_case.task.create_task.CreateTaskInputData;

public class CreateTaskController {

    private final CreateTaskInputBoundary interactor;

    public CreateTaskController(CreateTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Execute the Create Task Use Case.
     * @param eventId the task's event ID
     * @param description the task's description
     */
    public void execute(String eventId, String description) {
        interactor.execute(
                new CreateTaskInputData(eventId, description)
        );
    }
}
