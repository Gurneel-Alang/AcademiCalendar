package use_case.toggle_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import data_access.CheckListDataAccessObject;
import entity.task.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.task.CommonTaskFactory;
import use_case.task.toggle_task.*;

public class ToggleTaskInteractorTest {

    private CheckListDataAccessObject dataAccessObject;

    @BeforeEach
    public void setUp() {
        dataAccessObject = new CheckListDataAccessObject();
    }

    @Test
    public void togglesIncompleteTaskToComplete() {
        dataAccessObject.save(new Task("id-1", "Task A", null, false));

        final ToggleTaskOutputBoundary presenter = new ToggleTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(ToggleTaskOutputData outputData) {
                assertEquals("id-1", outputData.getTaskId());
                assertTrue(outputData.isCompleted());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure view should not be called.");
            }
        };

        final ToggleTaskInteractor interactor = new ToggleTaskInteractor(dataAccessObject, presenter);

        interactor.execute(new ToggleTaskInputData("id-1"));

        assertTrue(dataAccessObject.getById("id-1").isCompleted());
    }

    @Test
    public void togglesCompleteTaskToIncomplete() {dataAccessObject.save(new Task("id-1", "Task A", null, true));
        final ToggleTaskOutputBoundary presenter = new ToggleTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(ToggleTaskOutputData outputData) {
                assertEquals("id-1", outputData.getTaskId());
                assertFalse(outputData.isCompleted());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Failure view should not be called.");
            }
        };

        final ToggleTaskInteractor interactor = new ToggleTaskInteractor(dataAccessObject, presenter);

        interactor.execute(new ToggleTaskInputData("id-1"));

        assertFalse(dataAccessObject.getById("id-1").isCompleted());
    }
}