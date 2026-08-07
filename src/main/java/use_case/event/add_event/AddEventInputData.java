package use_case.event.add_event;

import java.time.LocalDate;

/**
 * The input data for the Add Event Use Case.
 */
public class AddEventInputData {

    private final String title;
    private final String description;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public AddEventInputData(String title, String description, LocalDate startDate, LocalDate endDate) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Return the title.
     * @return the title
     */
    String getTitle() {
        return title;
    }

    /**
     * Return the description.
     * @return the description
     */
    String getDescription() {
        return description;
    }

    /**
     * Return the start date.
     * @return the start date
     */
    LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Return the end date.
     * @return the end date
     */
    LocalDate getEndDate() {
        return endDate;
    }
}
