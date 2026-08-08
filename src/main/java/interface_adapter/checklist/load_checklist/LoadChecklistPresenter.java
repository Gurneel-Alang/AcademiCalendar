// interface_adapter/checklist/load_checklist/LoadChecklistPresenter.java
package interface_adapter.checklist.load_checklist;

import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.ChecklistState;
import interface_adapter.checklist.TaskState;
import use_case.task.load_checklist.LoadChecklistOutputBoundary;
import use_case.task.load_checklist.LoadChecklistOutputData;
import entity.task.Task;

import java.util.List;
import java.util.stream.Collectors;

public class LoadChecklistPresenter implements LoadChecklistOutputBoundary {
    private final ChecklistViewModel viewModel;

    public LoadChecklistPresenter(ChecklistViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LoadChecklistOutputData outputData) {
        final List<TaskState> taskStates = outputData.getTasks().stream()
                .map(t -> new TaskState(t.getId(), t.getDescription(), t.getDueDate(), t.isCompleted()))
                .collect(Collectors.toList());

        final ChecklistState state = viewModel.getState();
        state.setTasks(taskStates);
        state.setErrorMessage("");
        viewModel.firePropertyChanged();
    }
}