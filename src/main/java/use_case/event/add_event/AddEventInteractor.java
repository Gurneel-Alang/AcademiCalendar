package use_case.event.add_event;

import java.time.LocalDate;

import entity.event.EventFactoryInterface;
import entity.event.EventInterface;

/**
 * The Add Event Use Case Interactor.
 */
public class AddEventInteractor implements AddEventInputBoundary {

    private final AddEventDataAccessInterface eventDataAccessObject;
    private final AddEventOutputBoundary addEventPresenter;
    private final EventFactoryInterface eventFactory;

    public AddEventInteractor(AddEventDataAccessInterface eventDataAccessObject,
                              AddEventOutputBoundary addEventPresenter, EventFactoryInterface eventFactory) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.addEventPresenter = addEventPresenter;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(AddEventInputData addEventInputData) {
        final String title = addEventInputData.getTitle();
        final String description = addEventInputData.getDescription();
        final LocalDate startDate = addEventInputData.getStartDate();
        final LocalDate endDate = addEventInputData.getEndDate();

        if (eventDataAccessObject.existsByTitle(title)) {
            addEventPresenter.prepareFailView("Failed; event of given title already exists.");
        }
        else if (endDate.isBefore(startDate)) {
            addEventPresenter.prepareFailView("Failed; end date must come after start date.");
        }
        else {
            final EventInterface event = eventFactory.create(title, description, startDate, endDate);
            eventDataAccessObject.addEvent(event);

            final AddEventOutputData addEventOutputData = new AddEventOutputData(false);
            addEventPresenter.prepareSuccessView(addEventOutputData);
        }
    }

    @Override
    public void switchToMainView() {
        addEventPresenter.switchToMainView();
    }
}
