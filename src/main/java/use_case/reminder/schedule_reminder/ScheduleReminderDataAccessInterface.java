package use_case.reminder.schedule_reminder;

import entity.reminder.ReminderChoices;

/**
 * DAI used to ensure the reminder is fired in Java Swing.
 */
public interface ScheduleReminderDataAccessInterface {

    /**
     * Schedule upcoming reminder, haven't fired yet.
     * @param eventTitle inputting the name of the event
     * @param option option selected between custom or preset
     * @param onFire handler sent to scheduler
     * @return number of seconds until reminder go off
     */
    long schedule(String eventTitle, ReminderChoices option, ReminderFiredListener onFire);

    /**
     * When Schedule reminder fired, passing it to presenter to notify popup.
     */
    interface ReminderFiredListener {
        void fired(String eventTitle, ReminderChoices option);
    }
}
