package interface_adapter.event.delete_event;

import use_case.event.delete_event.DeleteEventInputBoundary;
import use_case.event.delete_event.DeleteEventInputData;

/**
 * The controller for the Delete Event Use Case.
 */
public class DeleteEventController {

    private final DeleteEventInputBoundary deleteEventUseCaseInteractor;

    public DeleteEventController(DeleteEventInputBoundary deleteEventUseCaseInteractor) {
        this.deleteEventUseCaseInteractor = deleteEventUseCaseInteractor;
    }

    /**
     * Execute the Delete Event Use Case.
     * @param title the title of an event to delete
     */
    public void execute(String title) {
        final DeleteEventInputData deleteEventInputData = new DeleteEventInputData(title);
        deleteEventUseCaseInteractor.execute(deleteEventInputData);
    }

    /**
     * Execute the "switch to main view" Use Case.
     */
    public void switchToMainView() {
        deleteEventUseCaseInteractor.switchToMainView();
    }
}
