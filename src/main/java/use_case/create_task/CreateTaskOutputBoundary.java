package use_case.create_task;
// doesnt know swing exists -> only knows some output boundary can present success or failure
/**
 * Output boundary for the Create Task use case
 */
public interface CreateTaskOutputBoundary {
    void prepareSuccessView(CreateTaskOutputData outputData);
    void prepareFailView(String errorMessage);
}