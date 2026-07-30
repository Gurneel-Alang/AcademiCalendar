package interface_adapter.create_task;

import use_case.create_task.CreateTaskOutputBoundary;
import use_case.create_task.CreateTaskOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts use-case output into checklist ViewModel state.
 */
public class CreateTaskPresenter
        implements CreateTaskOutputBoundary {

    private final ChecklistViewModel checklistViewModel;

    public CreateTaskPresenter(
            ChecklistViewModel checklistViewModel
    ) {
        this.checklistViewModel = checklistViewModel;
    }

    @Override
    public void prepareSuccessView(
            CreateTaskOutputData outputData
    ) {
        final ChecklistState currentState =
                checklistViewModel.getState();

        final ChecklistState newState =
                new ChecklistState(currentState);

        final List<TaskState> updatedTasks =
                new ArrayList<>(currentState.getTasks());

        final TaskState newTaskState =
                new TaskState(
                        outputData.getTaskId(),
                        outputData.getDescription(),
                        outputData.isCompleted()
                );

        updatedTasks.add(newTaskState);

        newState.setEventId(outputData.getEventId());
        newState.setTasks(updatedTasks);
        newState.setErrorMessage(null);

        checklistViewModel.setState(newState);
        checklistViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ChecklistState currentState =
                checklistViewModel.getState();

        final ChecklistState newState =
                new ChecklistState(currentState);

        newState.setErrorMessage(errorMessage);

        checklistViewModel.setState(newState);
        checklistViewModel.firePropertyChanged();
    }
}