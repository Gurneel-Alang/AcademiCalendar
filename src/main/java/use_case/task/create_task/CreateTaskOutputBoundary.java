package use_case.task.create_task;

public interface CreateTaskOutputBoundary {
    void prepareSuccessView(CreateTaskOutputData outputData);

    void prepareFailView(String errorMessage);
}
