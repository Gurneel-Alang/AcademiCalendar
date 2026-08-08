package interface_adapter.event.edit_event;

import java.time.LocalDate;

import use_case.event.edit_event.EditEventInputBoundary;
import use_case.event.edit_event.EditEventInputData;

/**
 * The controller for the Edit Event Use Case.
 */
public class EditEventController {

    private final EditEventInputBoundary editEventUseCaseInteractor;

    public EditEventController(EditEventInputBoundary editEventUseCaseInteractor) {
        this.editEventUseCaseInteractor = editEventUseCaseInteractor;
    }

    /**
     * Execute the Edit Event Use Case.
     * @param oldTitle the title of an event to edit
     * @param newTitle the new title
     * @param newDescription the new description
     * @param newStartDate the new start date
     * @param newEndDate the new end date
     */
    public void execute(String oldTitle, String newTitle, String newDescription,
                        LocalDate newStartDate, LocalDate newEndDate) {
        final EditEventInputData editEventInputData = new EditEventInputData(
                oldTitle, newTitle, newDescription, newStartDate, newEndDate);
        editEventUseCaseInteractor.execute(editEventInputData);
    }

    /**
     * Execute the "switch to main view" Use Case.
     */
    public void switchToMainView() {
        editEventUseCaseInteractor.switchToMainView();
    }
}
