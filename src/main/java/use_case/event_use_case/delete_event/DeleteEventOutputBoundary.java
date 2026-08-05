package use_case.event_use_case.delete_event;

/**
 * The output boundary for actions related to deleting events.
 */
public interface DeleteEventOutputBoundary {

    /**
     * Prepare the success view for the Delete Event Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(DeleteEventOutputData outputData);

    /**
     * Prepare the failure view for the Delete Event Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switch to the main view.
     */
    void switchToMainView();
}
