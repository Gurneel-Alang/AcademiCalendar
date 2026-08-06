package use_case.add_event;

import data_access.EventDataAccessObject;
import entity.event.EventFactory;
import entity.event.EventFactoryInterface;
import entity.event.EventInterface;
import org.junit.jupiter.api.Test;
import use_case.event_use_case.add_event.*;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class AddEventInteractorTest {

    @Test
    void successTest() {
        String title = "CSC207 Study Session";
        String description = "Study with friends in Toronto for the CSC207 final exam.";
        LocalDate startDate = LocalDate.of(2026, Month.AUGUST, 11);
        LocalDate endDate = LocalDate.of(2026, Month.AUGUST, 12);

        AddEventInputData inputData = new AddEventInputData(title, description, startDate, endDate);
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // Create a successPresenter that tests whether the test case is as expected.
        AddEventOutputBoundary successPresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                // Check if the output data is correct.
                assertEquals(false, outputData.isUseCaseFailed());
                // Check if the event was added to the DAO.
                assertTrue(eventRepository.existsByTitle(title));
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

        AddEventInputBoundary interactor = new AddEventInteractor(eventRepository,
                successPresenter, new EventFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureEventExistsTest() {
        String title = "CSC207 Study Session";
        String description = "Study with friends in Toronto for the CSC207 final exam.";
        LocalDate startDate = LocalDate.of(2026, Month.AUGUST, 11);
        LocalDate endDate = LocalDate.of(2026, Month.AUGUST, 12);

        AddEventInputData inputData = new AddEventInputData(title, description, startDate, endDate);
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // Add another "CSC207 Study Session" event for this test.
        EventFactoryInterface factory = new EventFactory();
        EventInterface event = factory.create("CSC207 Study Session", "Copy",
                LocalDate.of(2026, Month.AUGUST, 13),
                LocalDate.of(2026, Month.AUGUST, 14));
        eventRepository.addEvent(event);

        // Create a failurePresenter that tests whether the test case is as expected.
        AddEventOutputBoundary failurePresenter = new AddEventOutputBoundary() {
            @Override
            public void prepareSuccessView(AddEventOutputData outputData) {
                // This should never be reached, since the test case should fail.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Failed; event of given title already exists.", errorMessage);
            }

            @Override
            public void switchToMainView() {
                // This is expected.
            }
        };

        AddEventInputBoundary interactor = new AddEventInteractor(eventRepository,
                failurePresenter, new EventFactory());
        interactor.execute(inputData);
    }
}
