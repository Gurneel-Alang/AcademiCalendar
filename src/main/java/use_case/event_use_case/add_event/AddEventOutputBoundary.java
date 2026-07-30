package use_case.event_use_case.add_event;

/**
 * The output boundary for actions related to adding events.
 */
public interface AddEventOutputBoundary {

    /**
     * Prepare the success view for the Add Event Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddEventOutputData outputData);

    /**
     * Prepare the failure view for the Add Event Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
