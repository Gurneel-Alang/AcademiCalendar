package interface_adapter.event.view_events;

import java.time.LocalDate;

/**
 * A read-only, display-ready summary of an event.
 */
public class EventSummary {

    private final String title;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public EventSummary(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
