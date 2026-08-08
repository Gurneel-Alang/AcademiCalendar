package interface_adapter.checklist.toggle_task;

import use_case.task.toggle_task.ToggleTaskOutputBoundary;
import use_case.task.toggle_task.ToggleTaskOutputData;
import interface_adapter.checklist.TaskState;
import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.ChecklistState;

public class ToggleTaskPresenter
        implements ToggleTaskOutputBoundary {

    private final ChecklistViewModel viewModel;

    public ToggleTaskPresenter(ChecklistViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ToggleTaskOutputData outputData) {
        final ChecklistState state = viewModel.getState();

        for (TaskState task : state.getTasks()) {
            if (task.getId().equals(outputData.getTaskId())) {
                state.replaceTask(
                        new TaskState(
                                task.getId(),
                                task.getDescription(),
                                task.getDueDate(),
                                outputData.isCompleted()
                        )
                );
                break;
            }
        }

        state.setErrorMessage("");
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.getState().setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}
