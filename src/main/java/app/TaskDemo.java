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

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import entity.task.Task;

import java.util.ArrayList;
import java.util.List;

public final class TaskDemo {

    private TaskDemo() {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskDemo::createWindow);
    }

    private static void createWindow() {
        /*
         * Fake DAO used only for this demo.
         *
         * It satisfies AddTaskDataAccessInterface but does not
         * permanently save the task anywhere.
         */
        final AddTaskDataAccessInterface fakeTaskDAO =
                new AddTaskDataAccessInterface() {
                    @Override
                    public void save(Task task) {
                        System.out.println("Task saved: " + task);
                    }

                    @Override
                    public void saveTask(Task task) {

                    }

                    @Override
                    public List<Task> getTasksForEvent(String eventId) {
                        return new ArrayList<>();
                    }
                };

        final ChecklistViewModel checklistViewModel =
                new ChecklistViewModel();

        final CreateTaskOutputBoundary presenter =
                new CreateTaskPresenter(checklistViewModel);

        final TaskFactory taskFactory =
                new CommonTaskFactory();

        final CreateTaskInputBoundary interactor =
                new CreateTaskInteractor(
                        fakeTaskDAO,
                        presenter,
                        taskFactory
                );

        final CreateTaskController controller =
                new CreateTaskController(interactor);

        final EventDetailsView eventDetailsView =
                new EventDetailsView(
                        "eventId_123",
                        "Stats Exam",
                        controller,
                        checklistViewModel
                );

        final JFrame frame =
                new JFrame("Create Task Demo");

        frame.setDefaultCloseOperation(
                WindowConstants.EXIT_ON_CLOSE
        );

        frame.setContentPane(eventDetailsView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
