package use_case.reminder.schedule_reminder;

/**
 * Output data for the Schedule Reminder use case.
 */
public class ScheduleReminderOutputData {

    private final String eventTitle;
    private final String label;
    private final String pastLabel;
    private final long secondsUntilFire;

    public ScheduleReminderOutputData(String eventTitle, String label,
                                      String pastLabel, long secondsUntilFire) {
        this.eventTitle = eventTitle;
        this.label = label;
        this.pastLabel = pastLabel;
        this.secondsUntilFire = secondsUntilFire;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public String getLabel() {
        return label;
    }

    public String getPastLabel() {
        return pastLabel;
    }

    public long getSecondsUntilFire() {
        return secondsUntilFire;
    }
}
