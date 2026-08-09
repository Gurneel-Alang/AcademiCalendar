package interface_adapter.reminder.schedule_reminder;

/**
 * View state that stores current confirmation message, and current state of the Schedule Reminder use case.
 */
public class ScheduleReminderState {

    private String confirmationMessage = "";
    private String firedEventTitle = "";
    private String firedPastLabel = "";

    public String getConfirmationMessage() {
        return confirmationMessage;
    }

    public void setConfirmationMessage(String confirmationMessage) {
        this.confirmationMessage = confirmationMessage;
    }

    public String getFiredEventTitle() {
        return firedEventTitle;
    }

    public void setFiredEventTitle(String firedEventTitle) {
        this.firedEventTitle = firedEventTitle;
    }

    public String getFiredPastLabel() {
        return firedPastLabel;
    }

    public void setFiredPastLabel(String firedPastLabel) {
        this.firedPastLabel = firedPastLabel;
    }
}

