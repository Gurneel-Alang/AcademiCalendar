package use_case.delete_event;

import data_access.EventDataAccessObject;
import entity.event.EventFactory;
import entity.event.EventFactoryInterface;
import entity.event.EventInterface;
import org.junit.jupiter.api.Test;
import use_case.event.delete_event.*;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteEventInteractorTest {

    @Test
    void successTest() {
        DeleteEventInputData inputData = new DeleteEventInputData("CSC207 Study Session");
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // First add an event of the corresponding title for this test.
        EventFactoryInterface factory = new EventFactory();
        EventInterface event = factory.create("CSC207 Study Session",
                "Study with friends in Toronto for the CSC207 final exam.",
                LocalDate.of(2026, Month.AUGUST, 11),
                LocalDate.of(2026, Month.AUGUST, 12));
        eventRepository.addEvent(event);

        // Create a successPresenter that tests whether the test case is as expected.
        DeleteEventOutputBoundary successPresenter = new DeleteEventOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteEventOutputData outputData) {
                // Check if the output data is correct.
                assertEquals(false, outputData.isUseCaseFailed());
                // Check if the event is no longer in the DAO.
                assertFalse(eventRepository.existsByTitle("CSC207 Study Session"));
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void switchToMainView() {
                // This is expected.
            }
        };

        DeleteEventInputBoundary interactor = new DeleteEventInteractor(eventRepository,
                successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEventDoesNotExistTest() {
        DeleteEventInputData inputData = new DeleteEventInputData("CSC207 Study Session");
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // Create a failurePresenter that tests whether the test case is as expected.
        DeleteEventOutputBoundary failurePresenter = new DeleteEventOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteEventOutputData outputData) {
                // This should never be reached, since the test case should fail.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Failed; event of given title does not exist.", errorMessage);
            }

            @Override
            public void switchToMainView() {
                // This is expected.
            }
        };

        DeleteEventInputBoundary interactor = new DeleteEventInteractor(eventRepository,
                failurePresenter);
        interactor.execute(inputData);
    }
}
