package interface_adapter.checklist.toggle_task;

import use_case.task.toggle_task.ToggleTaskInputBoundary;
import use_case.task.toggle_task.ToggleTaskInputData;

public class ToggleTaskController {

    private final ToggleTaskInputBoundary interactor;

    public ToggleTaskController(ToggleTaskInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Execute the Toggle Task Use Case.
     * @param taskId the task's ID
     */
    public void execute(String taskId) {
        interactor.execute(new ToggleTaskInputData(taskId));
    }
}
