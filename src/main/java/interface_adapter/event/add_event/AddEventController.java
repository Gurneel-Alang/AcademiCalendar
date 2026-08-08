package interface_adapter.event.add_event;

import java.time.LocalDate;

import use_case.event.add_event.AddEventInputBoundary;
import use_case.event.add_event.AddEventInputData;

/**
 * The controller for the Add Event Use Case.
 */
public class AddEventController {

    private final AddEventInputBoundary addEventUseCaseInteractor;

    public AddEventController(AddEventInputBoundary addEventUseCaseInteractor) {
        this.addEventUseCaseInteractor = addEventUseCaseInteractor;
    }

    /**
     * Execute the Add Event Use Case.
     * @param title the title of an event to add
     * @param description the description of an event to add
     * @param startDate the start date of an event to add
     * @param endDate the end date of an event to add
     */
    public void execute(String title, String description, LocalDate startDate, LocalDate endDate) {
        final AddEventInputData addEventInputData = new AddEventInputData(
                title, description, startDate, endDate);
        addEventUseCaseInteractor.execute(addEventInputData);
    }

    /**
     * Execute the "switch to main view" Use Case.
     */
    public void switchToMainView() {
        addEventUseCaseInteractor.switchToMainView();
    }
}
