package use_case.event_use_case.delete_event;

import use_case.event_use_case.add_event.AddEventDataAccessInterface;
import use_case.event_use_case.add_event.AddEventOutputBoundary;

/**
 * The Delete Event Use Case Interactor.
 */
public class DeleteEventInteractor implements DeleteEventInputBoundary {

    private final DeleteEventDataAccessInterface eventDataAccessObject;
    private final DeleteEventOutputBoundary deleteEventPresenter;

    public DeleteEventInteractor(DeleteEventDataAccessInterface eventDataAccessObject,
                                 DeleteEventOutputBoundary deleteEventPresenter) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.deleteEventPresenter = deleteEventPresenter;
    }

    @Override
    public void execute(DeleteEventInputData deleteEventInputData) {
        final String title = deleteEventInputData.getTitle();

        if (eventDataAccessObject.existsByTitle(title)) {
            eventDataAccessObject.deleteEvent(title);

            final DeleteEventOutputData deleteEventOutputData = new DeleteEventOutputData(false);
            deleteEventPresenter.prepareSuccessView(deleteEventOutputData);
        } else {
            deleteEventPresenter.prepareFailView("Failed; event of given title does not exist.");
        }
    }

    @Override
    public void switchToMainView() { deleteEventPresenter.switchToMainView(); }
}
