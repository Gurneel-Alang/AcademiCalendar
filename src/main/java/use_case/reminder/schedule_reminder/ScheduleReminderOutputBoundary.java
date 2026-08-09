package use_case.reminder.schedule_reminder;

/**
 * Output boundary for Reminder Scheduler use case, outputting either a confirmation or
 * failed message, and a confirmation that the reminder timer has fired.
 */
public interface ScheduleReminderOutputBoundary {

    /**
     * Confirm a reminder was scheduled, including when it will fire.
     * @param outputData the scheduled reminder details
     */
    void prepareSuccessView(ScheduleReminderOutputData outputData);

    /**
     * Confirm that the reminder can NOT be scheduled.
     * @param errorMessage the reason
     */
    void prepareFailView(String errorMessage);

    /**
     * Notify that a scheduled reminder has now fired.
     * @param outputData the fired reminder details
     */
    void reminderFired(ScheduleReminderOutputData outputData);
}
