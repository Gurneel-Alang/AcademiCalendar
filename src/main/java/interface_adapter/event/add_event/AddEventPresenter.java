package interface_adapter.event.add_event;

import use_case.event_use_case.add_event.AddEventOutputBoundary;
import use_case.event_use_case.add_event.AddEventOutputData;

/**
 * The presenter for the Add Event Use Case.
 */
public class AddEventPresenter implements AddEventOutputBoundary {

    private final AddEventViewModel addEventViewModel;

    public AddEventPresenter(AddEventViewModel addEventViewModel) {
        this.addEventViewModel = addEventViewModel;
    }

    @Override
    public void prepareSuccessView(AddEventOutputData outputData) {
        final AddEventState state = addEventViewModel.getState();
        state.setErrorMessage(null);
        addEventViewModel.setState(state);
        addEventViewModel.firePropertyChanged(AddEventViewModel.CLOSE_PROPERTY);

        switchToMainView();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final AddEventState state = addEventViewModel.getState();
        state.setErrorMessage(errorMessage);
        addEventViewModel.setState(state);
        addEventViewModel.firePropertyChanged(AddEventViewModel.ERROR_PROPERTY);
    }

    @Override
    public void switchToMainView() {
        addEventViewModel.firePropertyChanged(AddEventViewModel.CLOSE_PROPERTY);
    }
}
