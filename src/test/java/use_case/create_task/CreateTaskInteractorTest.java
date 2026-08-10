package use_case.task.create_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import data_access.CheckListDataAccessObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import entity.task.CommonTaskFactory;
import data_access.CheckListDataAccessObject;

public class CreateTaskInteractorTest {

    private CheckListDataAccessObject dataAccessObject;

    @BeforeEach
    public void setUp() {
        dataAccessObject = new CheckListDataAccessObject();
    }

    @Test
    public void successfulCreateSavesTaskAndCallsSuccessView() {
        final LocalDate dueDate = LocalDate.of(2026, 8, 20);

        final CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData outputData) {
                assertEquals("Study week 3", outputData.getDescription());
                assertEquals(dueDate, outputData.getDueDate());
                assertFalse(outputData.isCompleted());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not fail: " + errorMessage);
            }
        };

        final CreateTaskInteractor interactor = new CreateTaskInteractor(
                dataAccessObject, presenter, new CommonTaskFactory());

        interactor.execute(new CreateTaskInputData("Study week 3", dueDate));

        assertEquals(1, dataAccessObject.getAll().size());
    }

    @Test
    public void createWithNullDueDateSucceeds() {
        final CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData outputData) {
                assertNull(outputData.getDueDate());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Should not fail: " + errorMessage);
            }
        };

        final CreateTaskInteractor interactor = new CreateTaskInteractor(
                dataAccessObject, presenter, new CommonTaskFactory());

        interactor.execute(new CreateTaskInputData("No date task", null));
    }

    @Test
    public void blankDescriptionCallsFailView() {
        final boolean[] failCalled = {false};

        final CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failCalled[0] = true;
                assertEquals("Task description cannot be empty.", errorMessage);
            }
        };

        final CreateTaskInteractor interactor = new CreateTaskInteractor(
                dataAccessObject, presenter, new CommonTaskFactory());

        interactor.execute(new CreateTaskInputData("   ", null));

        assertTrue(failCalled[0]);
        assertEquals(0, dataAccessObject.getAll().size());
    }

    @Test
    public void nullDescriptionCallsFailView() {
        final boolean[] failCalled = {false};

        final CreateTaskOutputBoundary presenter = new CreateTaskOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateTaskOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                failCalled[0] = true;
            }
        };

        final CreateTaskInteractor interactor = new CreateTaskInteractor(
                dataAccessObject, presenter, new CommonTaskFactory());

        interactor.execute(new CreateTaskInputData(null, null));

        assertTrue(failCalled[0]);
    }
}
