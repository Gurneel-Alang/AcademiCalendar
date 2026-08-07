package interface_adapter.checklist.create_task;

import use_case.task.create_task.CreateTaskInputBoundary;
import use_case.task.create_task.CreateTaskInputData;

public class CreateTaskController {
    private final CreateTaskInputBoundary interactor;

    public CreateTaskController(CreateTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String description) {
        interactor.execute(new CreateTaskInputData(description));
    }
}
