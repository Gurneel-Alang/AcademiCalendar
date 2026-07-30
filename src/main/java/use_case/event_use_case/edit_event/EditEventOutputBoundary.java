package use_case.event_use_case.edit_event;

/**
 * The output boundary for actions related to editing events.
 */
public interface EditEventOutputBoundary {

    /**
     * Prepare the success view for the Edit Event Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(EditEventOutputData outputData);

    /**
     * Prepare the failure view for the Edit Event Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
