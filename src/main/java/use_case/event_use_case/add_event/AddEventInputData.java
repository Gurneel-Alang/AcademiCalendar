package use_case.event_use_case.add_event;

import java.util.Date;

/**
 * The input data for the Add Event Use Case.
 */
public class AddEventInputData {

    private final String title;
    private final String description;
    private final Date startDate;
    private final Date endDate;

    public AddEventInputData(String title, String description,
                             Date startDate, Date endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    String getTitle() { return title; }

    String getDescription() { return description; }

    Date getStartDate() { return startDate; }

    Date getEndDate() { return endDate; }
}
