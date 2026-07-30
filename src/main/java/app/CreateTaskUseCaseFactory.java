package app;

import entity.task.CommonTaskFactory;
import entity.task.TaskFactory;
import interface_adapter.create_task.ChecklistViewModel;
import interface_adapter.create_task.CreateTaskController;
import interface_adapter.create_task.CreateTaskPresenter;
import use_case.create_task.AddTaskDataAccessInterface;
import use_case.create_task.CreateTaskInputBoundary;
import use_case.create_task.CreateTaskInteractor;
import use_case.create_task.CreateTaskOutputBoundary;
import view.EventDetailsView;

/**
 * Wires together the Create Task use case.
 */
public final class CreateTaskUseCaseFactory {

    private CreateTaskUseCaseFactory() {
        // Utility class.
    }

    public static EventDetailsView create(
            String eventId,
            String eventName,
            AddTaskDataAccessInterface taskDataAccessObject
    ) {
        final ChecklistViewModel checklistViewModel =
                new ChecklistViewModel();

        final CreateTaskOutputBoundary presenter =
                new CreateTaskPresenter(
                        checklistViewModel
                );

        final TaskFactory taskFactory =
                new CommonTaskFactory();

        final CreateTaskInputBoundary interactor =
                new CreateTaskInteractor(
                        taskDataAccessObject,
                        presenter,
                        taskFactory
                );

        final CreateTaskController controller =
                new CreateTaskController(interactor);

        return new EventDetailsView(
                eventId,
                eventName,
                controller,
                checklistViewModel
        );
    }
}