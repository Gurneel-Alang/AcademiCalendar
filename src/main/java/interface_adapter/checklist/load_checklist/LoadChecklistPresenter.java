package interface_adapter.checklist.load_checklist;

import java.util.List;

import use_case.task.load_checklist.LoadChecklistOutputBoundary;
import use_case.task.load_checklist.LoadChecklistOutputData;
import interface_adapter.checklist.ChecklistState;
import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.TaskState;

public class LoadChecklistPresenter implements LoadChecklistOutputBoundary {
    private final ChecklistViewModel viewModel;

    public LoadChecklistPresenter(ChecklistViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(
            LoadChecklistOutputData outputData
    ) {
        final List<TaskState> tasks =
                outputData.getTasks()
                        .stream()
                        .map(task -> new TaskState(
                                task.getId(),
                                task.getDescription(),
                                task.isCompleted()
                        ))
                        .toList();

        final ChecklistState state = viewModel.getState();
        state.setEventId(outputData.getEventId());
        state.setTasks(tasks);
        state.setErrorMessage("");

        viewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.getState().setErrorMessage(errorMessage);
        viewModel.firePropertyChanged();
    }
}
