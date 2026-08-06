package use_case.task.toggle_task;

public interface ToggleTaskOutputBoundary {
    void prepareSuccessView(ToggleTaskOutputData outputData);

    void prepareFailView(String errorMessage);
}
