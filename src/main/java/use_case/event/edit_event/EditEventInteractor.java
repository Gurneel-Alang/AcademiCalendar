package use_case.event.edit_event;

import java.time.LocalDate;

import entity.event.EventFactoryInterface;
import entity.event.EventInterface;

/**
 * The Edit Event Use Case Interactor.
 */
public class EditEventInteractor implements EditEventInputBoundary {

    private final EditEventDataAccessInterface eventDataAccessObject;
    private final EditEventOutputBoundary editEventPresenter;
    private final EventFactoryInterface eventFactory;

    public EditEventInteractor(EditEventDataAccessInterface eventDataAccessObject,
                               EditEventOutputBoundary editEventPresenter, EventFactoryInterface eventFactory) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.editEventPresenter = editEventPresenter;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(EditEventInputData editEventInputData) {
        final String oldTitle = editEventInputData.getOldTitle();
        final String newTitle = editEventInputData.getNewTitle();
        final String newDescription = editEventInputData.getNewDescription();
        final LocalDate newStartDate = editEventInputData.getNewStartDate();
        final LocalDate newEndDate = editEventInputData.getNewEndDate();

        if (eventDataAccessObject.existsByTitle(oldTitle)) {
            if (!(eventDataAccessObject.existsByTitle(newTitle)) || oldTitle.equals(newTitle)) {
                final EventInterface newEvent = eventFactory.create(newTitle, newDescription,
                        newStartDate, newEndDate);
                eventDataAccessObject.editEvent(oldTitle, newEvent);

                final EditEventOutputData editEventOutputData = new EditEventOutputData(false);
                editEventPresenter.prepareSuccessView(editEventOutputData);
            }
            else {
                editEventPresenter.prepareFailView("Failed; event of given new title"
                        + " already exists.");
            }
        }
        else {
            editEventPresenter.prepareFailView("Failed; event of given previous title"
                    + " does not exist.");
        }
    }

    @Override
    public void switchToMainView() {
        editEventPresenter.switchToMainView();
    }
}
