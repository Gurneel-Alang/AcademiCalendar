package entity.reminder;

public class ReminderChoices {
    private final String label;
    private final long offsetTime;
    private final String pastLabel;

    public ReminderChoices(String label, String pastLabel, long offsetTime){
        this.label = label;
        this.pastLabel = pastLabel;
        this.offsetTime = offsetTime;
    }

    /**
     * @return label shown in drop-down option.
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return past tense label
     */
    public String getPastLabel() {
        return pastLabel;
    }

    /**
     * @return event creations time, in seconds of real time.
     * Each second in real world is counted as a minute for testing purposes.
     */
    public long getOffsetTime() {
        return offsetTime;
    }

    /**
     * Change output from code output to String output.
     */
    @Override
    public String toString(){
        return label;
    }

    /**
     * Reminder options and their labels to display.
     * @return from available options of reminders.
     */
    public static ReminderChoices[] all(){
        return new ReminderChoices[]{
                new ReminderChoices("In 1 hour", "1 hour ago", 3600),
                new ReminderChoices("In 3 hours", "3 hours ago", 10800),
                new ReminderChoices("In 7 days", "7 days ago", 604800),
        };
    }

    public static ReminderChoices oneHour() {
        return new ReminderChoices("In 1 hour", "1 hour ago", 3600);
    }
}
