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

    private static final long CONVERT_TO_SECONDS = 60L;
    private static final int MILISECOND = 1000;

    @Override
    public long schedule(String eventTitle, ReminderChoices option, ReminderFiredListener onFire) {
        long seconds = option.getOffsetTime() / CONVERT_TO_SECONDS;
        int delay = (int) Math.min(seconds * MILISECOND, Integer.MAX_VALUE);

        Timer timer = new Timer(delay, event -> onFire.fired(eventTitle, option));
        timer.setRepeats(false);
        timer.start();

        return seconds;
    }
}

