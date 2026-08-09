package use_case.schedule_reminder;

import entity.reminder.ReminderChoices;
import org.junit.jupiter.api.Test;
import use_case.reminder.schedule_reminder.ScheduleReminderDataAccessInterface;
import use_case.reminder.schedule_reminder.ScheduleReminderInputBoundary;
import use_case.reminder.schedule_reminder.ScheduleReminderInputData;
import use_case.reminder.schedule_reminder.ScheduleReminderInteractor;
import use_case.reminder.schedule_reminder.ScheduleReminderOutputBoundary;
import use_case.reminder.schedule_reminder.ScheduleReminderOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test for Schedule Reminder Interactor.
 * This test pass the event title to the interactor, input and output data,
 * then call the presenter to ensure everything passes successfully.
 */

public class ScheduleReminderInteractorTest {

    @Test
    void successTest() {
        String eventTitle = "CSC207 Study Session";
        ReminderChoices option = ReminderChoices.oneHour();
        ScheduleReminderInputData inputData = new ScheduleReminderInputData(eventTitle, option);

        ScheduleReminderDataAccessInterface scheduler =
                (title, selectedOption, onFire) -> {
                    assertEquals(eventTitle, title);
                    assertEquals(option, selectedOption);
                    return 60L;
                };

        ScheduleReminderOutputBoundary successPresenter = new ScheduleReminderOutputBoundary() {
                    @Override
                    public void prepareSuccessView(ScheduleReminderOutputData outputData) {
                        assertEquals(eventTitle, outputData.getEventTitle());
                        assertEquals(option.getLabel(), outputData.getLabel());
                        assertEquals(option.getPastLabel(), outputData.getPastLabel());
                        assertEquals(60L, outputData.getSecondsUntilFire());
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        fail("Use case should not fail");
                    }

                    @Override
                    public void reminderFired(
                            ScheduleReminderOutputData outputData) {
                        fail("Reminder should not fire");
                    }
                };

        ScheduleReminderInputBoundary interactor =
                new ScheduleReminderInteractor(scheduler, successPresenter);
        interactor.execute(inputData);
    }

    /**
     * This test runs a scenario where the input for title is blank.
     * Passed through interactor, where it should detect the invalid title and
     * stop the process, and won't create a reminder.
     * This test fails if scheduler is called.
     */
    @Test
    void failureBlankEventTitleTest() {
        ReminderChoices option = ReminderChoices.oneHour();
        ScheduleReminderInputData inputData = new ScheduleReminderInputData("", option);

        ScheduleReminderDataAccessInterface scheduler =
                (title, selectedOption, onFire) -> {
                    fail("Scheduler should not be called if title check hasn't passed.");
                    return 0L;
                };

        ScheduleReminderOutputBoundary failurePresenter = new ScheduleReminderOutputBoundary() {

                    @Override
                    public void prepareSuccessView(
                            ScheduleReminderOutputData outputData) {
                        fail("Use case should not pass.");
                    }

                    @Override
                    public void prepareFailView(String errorMessage) {
                        assertEquals("A reminder needs an event title.", errorMessage);
                    }

                    @Override
                    public void reminderFired(ScheduleReminderOutputData outputData) {
                        fail("Reminder should not fire when scheduling fails.");
                    }
                };

        ScheduleReminderInputBoundary interactor =
                new ScheduleReminderInteractor(scheduler, failurePresenter);

        interactor.execute(inputData);
    }
}

