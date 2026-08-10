package use_case.task.load_checklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.task.Task;
import data_access.CheckListDataAccessObject;

public class LoadChecklistInteractorTest {

    private CheckListDataAccessObject dataAccessObject;

    @BeforeEach
    public void setUp() {
        dataAccessObject = new CheckListDataAccessObject();
    }

    @Test
    public void loadsAllSavedTasks() {
        dataAccessObject.save(new Task("id-1", "Task A", LocalDate.of(2026, 8, 10), false));
        dataAccessObject.save(new Task("id-2", "Task B", null, true));

        final LoadChecklistOutputBoundary presenter = new LoadChecklistOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadChecklistOutputData outputData) {
                assertEquals(2, outputData.getTasks().size());
            }
        };

        final LoadChecklistInteractor interactor =
                new LoadChecklistInteractor(dataAccessObject, presenter, new DueDateSortStrategy());

        interactor.execute();
    }

    @Test
    public void loadsEmptyListWhenNoTasksExist() {
        final LoadChecklistOutputBoundary presenter = new LoadChecklistOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadChecklistOutputData outputData) {
                assertTrue(outputData.getTasks().isEmpty());
            }
        };

        final LoadChecklistInteractor interactor =
                new LoadChecklistInteractor(dataAccessObject, presenter, new DueDateSortStrategy());

        interactor.execute();
    }

    @Test
    public void appliesSortStrategyBeforePresenting() {
        dataAccessObject.save(new Task("id-1", "Later", LocalDate.of(2026, 9, 1), false));
        dataAccessObject.save(new Task("id-2", "Earlier", LocalDate.of(2026, 8, 1), false));

        final LoadChecklistOutputBoundary presenter = new LoadChecklistOutputBoundary() {
            @Override
            public void prepareSuccessView(LoadChecklistOutputData outputData) {
                final List<Task> tasks = outputData.getTasks();
                assertEquals("id-2", tasks.get(0).getId());
                assertEquals("id-1", tasks.get(1).getId());
            }
        };

        final LoadChecklistInteractor interactor =
                new LoadChecklistInteractor(dataAccessObject, presenter, new DueDateSortStrategy());

        interactor.execute();
    }
}
