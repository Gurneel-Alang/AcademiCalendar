package use_case.task.load_checklist;

public class LoadChecklistInputData {
    private String eventId;

    public LoadChecklistInputData(String eventId) {
        this.eventId = eventId;
    }

    public String getEventId() {
        return eventId;
    }
}
