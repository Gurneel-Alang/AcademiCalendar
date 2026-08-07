package use_case.event_use_case.delete_event;

/**
 * The input boundary for actions related to deleting events.
 */
public interface DeleteEventInputBoundary {

    /**
     * Execute the Delete Event Use Case.
     * @param deleteEventInputData the input data
     */
    void execute(DeleteEventInputData deleteEventInputData);

    /**
     * Execute the "switch to main view" Use Case.
     */
    void switchToMainView();
}
