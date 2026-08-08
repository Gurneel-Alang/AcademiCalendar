package interface_adapter.checklist.create_task;

import java.time.LocalDate;

import use_case.task.create_task.CreateTaskInputBoundary;
import use_case.task.create_task.CreateTaskInputData;

public class CreateTaskController {

    private final CreateTaskInputBoundary interactor;

    public CreateTaskController(CreateTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String description, LocalDate dueDate) {
        interactor.execute(new CreateTaskInputData(description, dueDate));
    }
}
