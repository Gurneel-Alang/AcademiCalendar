package use_case.reminder.schedule_reminder;

import entity.reminder.ReminderChoices;

/**
 * Input data for the Schedule Reminder use case.
 */
public class ScheduleReminderInputData {

    private final String eventTitle;
    private final ReminderChoices option;

    public ScheduleReminderInputData(String eventTitle, ReminderChoices option) {
        this.eventTitle = eventTitle;
        this.option = option;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public ReminderChoices getOption() {
        return option;
    }
}
