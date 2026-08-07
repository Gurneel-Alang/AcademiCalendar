package use_case.event_use_case.edit_event;

/**
 * The input boundary for actions related to editing events.
 */
public interface EditEventInputBoundary {

    /**
     * Execute the Edit Event Use Case.
     * @param editEventInputData the input data
     */
    void execute(EditEventInputData editEventInputData);

    /**
     * Execute the "switch to main view" Use Case.
     */
    void switchToMainView();
}
