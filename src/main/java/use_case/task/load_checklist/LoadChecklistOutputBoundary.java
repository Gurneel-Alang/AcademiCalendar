package use_case.task.load_checklist;

public interface LoadChecklistOutputBoundary {
    void prepareSuccessView(LoadChecklistOutputData loadChecklistOutputData);

    void prepareFailView(String errorMessage);
}
