package use_case.edit_event;

import data_access.EventDataAccessObject;
import entity.event.EventFactory;
import entity.event.EventFactoryInterface;
import entity.event.EventInterface;
import org.junit.jupiter.api.Test;
import use_case.event.edit_event.*;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

public class EditEventInteractorTest {

    @Test
    void successSameTitlesTest() {
        String title = "CSC207 Study Session";
        String description = "Study at home for the CSC207 final exam.";
        LocalDate startDate = LocalDate.of(2026, Month.AUGUST, 13);
        LocalDate endDate = LocalDate.of(2026, Month.AUGUST, 14);

        EditEventInputData inputData = new EditEventInputData(title, title, description,
                startDate, endDate);
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // First add an event of the corresponding title for this test.
        EventFactoryInterface factory = new EventFactory();
        EventInterface event = factory.create(title,
                "Study with friends in Toronto for the CSC207 final exam.",
                LocalDate.of(2026, Month.AUGUST, 11),
                LocalDate.of(2026, Month.AUGUST, 12));
        eventRepository.addEvent(event);

        // Create a successPresenter that tests whether the test case is as expected.
        EditEventOutputBoundary successPresenter = new EditEventOutputBoundary() {
            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                // Check if the output data is correct.
                assertEquals(false, outputData.isUseCaseFailed());
                // Check if the event still exists in the DAO.
                assertTrue(eventRepository.existsByTitle(title));
            }

            @Override
            public void prepareFailView(String errorMessage) { fail("Use case failure is unexpected."); }

            @Override
            public void switchToMainView() {
                // This is expected.
            }
        };

        EditEventInputBoundary interactor = new EditEventInteractor(eventRepository,
                successPresenter, new EventFactory());
        interactor.execute(inputData);
    }

    @Test
    void successDifferentTitlesTest() {
        String oldTitle = "CSC207 Study Session";
        String newTitle = "CSC207 Study Session and Office Hours";
        String newDescription = "Study with friends in Toronto for the CSC207 final exam," +
                " and visit office hours after.";
        LocalDate startDate = LocalDate.of(2026, Month.AUGUST, 11);
        LocalDate endDate = LocalDate.of(2026, Month.AUGUST, 12);

        EditEventInputData inputData = new EditEventInputData(oldTitle, newTitle, newDescription,
                startDate, endDate);
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // First add an event of the corresponding old title for this test.
        EventFactoryInterface factory = new EventFactory();
        EventInterface event = factory.create(oldTitle,
                "Study with friends in Toronto for the CSC207 final exam.",
                LocalDate.of(2026, Month.AUGUST, 11),
                LocalDate.of(2026, Month.AUGUST, 12));
        eventRepository.addEvent(event);

        // Create a successPresenter that tests whether the test case is as expected.
        EditEventOutputBoundary successPresenter = new EditEventOutputBoundary() {
            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                // Check if the output data is correct.
                assertEquals(false, outputData.isUseCaseFailed());
                // Check if the old title does not exist in the DAO.
                assertFalse(eventRepository.existsByTitle(oldTitle));
                // Check if the new title exists in the DAO.
                assertTrue(eventRepository.existsByTitle(newTitle));
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

        EditEventInputBoundary interactor = new EditEventInteractor(eventRepository,
                successPresenter, new EventFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureOldEventNameDoesNotExistTest() {
        String title = "CSC207 Study Session";
        String description = "Study with friends in Toronto for the CSC207 final exam.";
        LocalDate startDate = LocalDate.of(2026, Month.AUGUST, 11);
        LocalDate endDate = LocalDate.of(2026, Month.AUGUST, 12);

        EditEventInputData inputData = new EditEventInputData(title, title, description,
                startDate, endDate);
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // Create a failurePresenter that tests whether the test case is as expected.
        EditEventOutputBoundary failurePresenter = new EditEventOutputBoundary() {
            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                // This should never be reached, since the test case should fail.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Failed; event of given previous title" +
                        " does not exist.", errorMessage);
            }

            @Override
            public void switchToMainView() {
                // This is expected.
            }
        };

        EditEventInputBoundary interactor = new EditEventInteractor(eventRepository,
                failurePresenter, new EventFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureNewEventNameExistsTest() {
        String oldTitle = "First Title";
        String newTitle = "Second Title";
        String newDescription = "Some new description.";
        LocalDate startDate = LocalDate.of(2026, Month.AUGUST, 15);
        LocalDate endDate = LocalDate.of(2026, Month.AUGUST, 16);

        EditEventInputData inputData = new EditEventInputData(oldTitle, newTitle, newDescription,
                startDate, endDate);
        EventDataAccessObject eventRepository = new EventDataAccessObject();

        // First make one event with the same oldTitle and one of the same newTitle for this test.
        EventFactoryInterface factory = new EventFactory();
        EventInterface eventOne = factory.create(oldTitle, "First description.",
                LocalDate.of(2026, Month.AUGUST, 11),
                LocalDate.of(2026, Month.AUGUST, 12));
        EventInterface eventTwo = factory.create(newTitle, "Second description.",
                LocalDate.of(2026, Month.AUGUST, 13),
                LocalDate.of(2026, Month.AUGUST, 14));
        eventRepository.addEvent(eventOne);
        eventRepository.addEvent(eventTwo);

        // Create a failurePresenter that tests whether the test case is as expected.
        EditEventOutputBoundary failurePresenter = new EditEventOutputBoundary() {
            @Override
            public void prepareSuccessView(EditEventOutputData outputData) {
                // This should never be reached, since the test case should fail.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Failed; event of given new title" +
                        " already exists.", errorMessage);
            }

            @Override
            public void switchToMainView() {
                // This is expected.
            }
        };

        EditEventInputBoundary interactor = new EditEventInteractor(eventRepository,
                failurePresenter, new EventFactory());
        interactor.execute(inputData);
    }
}
