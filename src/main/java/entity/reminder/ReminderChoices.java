package entity.reminder;

public class ReminderChoices {

    private static final int ONE_HOUR_OFFSET = 3600;
    private static final int THREE_HOUR_OFFSET = 10800;
    private static final int SEVEN_DAYS_OFFSET = 604800;
    private static final long CONVERT_TO_SECONDS = 3600L;

    private final String label;
    private final long offsetTime;
    private final String pastLabel;

    public ReminderChoices(String label, String pastLabel, long offsetTime) {
        this.label = label;
        this.pastLabel = pastLabel;
        this.offsetTime = offsetTime;
    }

    /**
     * Return the label shown in the drop-down option.
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * Return the past label.
     * @return the past label
     */
    public String getPastLabel() {
        return pastLabel;
    }

    /**
     * Return event creations time, in seconds of real time.
     * Each second in real time is counted as a minute for testing purposes.
     * @return event creations time
     */
    public long getOffsetTime() {
        return offsetTime;
    }

    /**
     * Change output from code output to String output.
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Reminder options and their labels to display.
     * @return from available options of reminders.
     */
    public static ReminderChoices[] all() {
        return new ReminderChoices[]{
            new ReminderChoices("In 1 hour", "1 hour ago", ONE_HOUR_OFFSET),
            new ReminderChoices("In 3 hours", "3 hours ago", THREE_HOUR_OFFSET),
            new ReminderChoices("In 7 days", "7 days ago", SEVEN_DAYS_OFFSET),
        };
    }

    /**
     * One hour static offset for snoozing option.
     * @return Label, pastlabel and offsetTime of 1 hour option
     */
    public static ReminderChoices oneHour() {
        return new ReminderChoices("In 1 hour", "1 hour ago", ONE_HOUR_OFFSET);
    }

    /**
     * Custom reminder, receiving input entered by user, then process it to match into seconds.
     * @param hours as in custom hours users want to set
     * @return a reminder that fires x hours after creation
     */
    public static ReminderChoices customHours(int hours) {
        return new ReminderChoices(
                "In " + hours + " hour(s)",
                hours + " hour(s) ago",
                hours * CONVERT_TO_SECONDS);
    }
}
