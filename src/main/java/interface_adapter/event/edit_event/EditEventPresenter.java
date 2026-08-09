package interface_adapter.event.edit_event;

import use_case.event.edit_event.EditEventOutputBoundary;
import use_case.event.edit_event.EditEventOutputData;

public class EditEventPresenter implements EditEventOutputBoundary {

    private final EditEventViewModel editEventViewModel;

    public EditEventPresenter(EditEventViewModel editEventViewModel) {
        this.editEventViewModel = editEventViewModel;
    }

    @Override
    public void prepareSuccessView(EditEventOutputData outputData) {
        final EditEventState state = editEventViewModel.getState();
        state.setErrorMessage(null);
        editEventViewModel.setState(state);
        switchToMainView();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final EditEventState state = editEventViewModel.getState();
        state.setErrorMessage(errorMessage);
        editEventViewModel.setState(state);
        editEventViewModel.firePropertyChanged(EditEventViewModel.ERROR_PROPERTY);
    }

    @Override
    public void switchToMainView() {
        editEventViewModel.firePropertyChanged(EditEventViewModel.CLOSE_PROPERTY);
    }
}
