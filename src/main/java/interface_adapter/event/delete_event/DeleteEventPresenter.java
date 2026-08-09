package interface_adapter.event.delete_event;

import use_case.event.delete_event.DeleteEventOutputBoundary;
import use_case.event.delete_event.DeleteEventOutputData;

/**
 * The presenter for the Delete Event Use Case.
 */
public class DeleteEventPresenter implements DeleteEventOutputBoundary {

    private final DeleteEventViewModel deleteEventViewModel;

    public DeleteEventPresenter(DeleteEventViewModel deleteEventViewModel) {
        this.deleteEventViewModel = deleteEventViewModel;
    }

    @Override
    public void prepareSuccessView(DeleteEventOutputData outputData) {
        final DeleteEventState state = deleteEventViewModel.getState();
        state.setErrorMessage(null);
        deleteEventViewModel.setState(state);
        switchToMainView();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final DeleteEventState state = deleteEventViewModel.getState();
        state.setErrorMessage(errorMessage);
        deleteEventViewModel.setState(state);
        deleteEventViewModel.firePropertyChanged(DeleteEventViewModel.ERROR_PROPERTY);
    }

    @Override
    public void switchToMainView() {
        deleteEventViewModel.firePropertyChanged(DeleteEventViewModel.CLOSE_PROPERTY);
    }
}
