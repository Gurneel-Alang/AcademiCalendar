package use_case.reminder.schedule_reminder;

import entity.reminder.ReminderChoices;

/**
 * DAI used to ensure the reminder is fired in Java Swing.
 */
public interface ScheduleReminderDataAccessInterface {

    long schedule(String eventTitle, ReminderChoices option, ReminderFiredListener onFire);

    interface ReminderFiredListener {
        void fired(String eventTitle, ReminderChoices option);
    }
}
