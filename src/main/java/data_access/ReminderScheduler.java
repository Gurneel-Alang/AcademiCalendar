package data_access;

import javax.swing.Timer;

import entity.reminder.ReminderChoices;
import use_case.reminder.schedule_reminder.ScheduleReminderDataAccessInterface;

/**
 * Implemented reminder using Java Swing timer.
 * Allow users to schedule one of the 3 preset values, or, custom value.
 * Each second is offset to minute.
 */

public class ReminderScheduler implements ScheduleReminderDataAccessInterface {

    private static final long SECONDS_PER_MINUTE = 60L;
    private static final int MILLIS_PER_SECOND = 1000;

    @Override
    public long schedule(String eventTitle, ReminderChoices option, ReminderFiredListener onFire) {
        long seconds = option.getOffsetTime() / SECONDS_PER_MINUTE;
        int delay = (int) (seconds * MILLIS_PER_SECOND);
        Timer timer = new Timer(delay, event -> onFire.fired(eventTitle, option));
        timer.setRepeats(false);
        timer.start();

        return seconds;
    }
}

