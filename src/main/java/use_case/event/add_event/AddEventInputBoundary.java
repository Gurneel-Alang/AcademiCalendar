package use_case.event.add_event;

/**
 * The input boundary for actions related to adding events.
 */
public interface AddEventInputBoundary {

    /**
     * Execute the Add Event Use Case.
     * @param addEventInputData the input data
     */
    void execute(AddEventInputData addEventInputData);

    /**
     * Execute the "switch to main view" Use Case.
     */
    void switchToMainView();
}
