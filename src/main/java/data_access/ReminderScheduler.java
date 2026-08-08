package data_access;

import javax.swing.Timer;

import entity.reminder.ReminderChoices;

/**
 * Implemented reminder using Java Swing timer.
 * In one hour will send a reminder in 60 seconds, while 3 and 7 days
 * will be 259200 and 604800 seconds respectively.
 */

public class ReminderScheduler {

    private static final long REAL_SECONDS_PER_SCHEDULED_SECOND = 60L;
    private static final int MILLIS_PER_SECOND = 1000;

    /**
     * Initialize a reminder for an event that was created.
     * @param eventTitle the event's title
     * @param option the reminder option
     * @param onFire the action
     */
    public void schedule(String eventTitle, ReminderChoices option, FinishedCountdown onFire) {
        final long scaledSeconds = option.getOffsetTime() / REAL_SECONDS_PER_SCHEDULED_SECOND;
        final long scaledMillis = scaledSeconds * MILLIS_PER_SECOND;
        final int delay = (int) Math.min(scaledMillis, Integer.MAX_VALUE);

        final Timer timer = new Timer(delay, event -> onFire.fire(eventTitle, option));
        timer.setRepeats(false);
        timer.start();
    }

    /**
     * Event action when timer finished countdown.
     */
    public interface FinishedCountdown {
        void fire(String eventTitle, ReminderChoices option);
    }
}
