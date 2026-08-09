package use_case.reminder.schedule_reminder;

/**
 * Input boundary for the Schedule Reminder use case.
 */
public interface ScheduleReminderInputBoundary {

    /**
     * Schedule a reminder upon the inputData request
     */
    void execute(ScheduleReminderInputData inputData);
}
