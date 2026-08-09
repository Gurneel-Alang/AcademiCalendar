package interface_adapter.reminder.schedule_reminder;

import entity.reminder.ReminderChoices;
import use_case.reminder.schedule_reminder.ScheduleReminderInputBoundary;
import use_case.reminder.schedule_reminder.ScheduleReminderInputData;

/**
 * Controller for schedule reminder use case.
 */

public class ScheduleReminderController {

    private final ScheduleReminderInputBoundary interactor;

    public ScheduleReminderController(ScheduleReminderInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Schedule a reminder.
     * @param eventTitle to parse current event title.
     * @param option select which of the time option (3 preset or custom) user is picking.
     */
    public void schedule(String eventTitle, ReminderChoices option) {
        interactor.execute(new ScheduleReminderInputData(eventTitle, option));
    }
}
