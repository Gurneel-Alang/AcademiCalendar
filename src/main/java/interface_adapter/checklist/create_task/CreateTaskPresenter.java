package interface_adapter.checklist.create_task;

import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.ChecklistState;
import interface_adapter.checklist.TaskState;
import use_case.task.create_task.CreateTaskOutputBoundary;
import use_case.task.create_task.CreateTaskOutputData;

public class CreateTaskPresenter
        implements CreateTaskOutputBoundary {

    private final ChecklistViewModel viewModel;

    public CreateTaskPresenter(ChecklistViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreateTaskOutputData outputData) {
        final ChecklistState state = viewModel.getState();

        state.addTask(
                new TaskState(
                        outputData.getTaskId(),
                        outputData.getDescription(),
                        outputData.getDueDate(),
                        outputData.isCompleted()
                )
        );

        state.setErrorMessage("");
        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.getState().setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}
