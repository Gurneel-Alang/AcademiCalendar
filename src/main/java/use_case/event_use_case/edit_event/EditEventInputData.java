package use_case.event_use_case.edit_event;

import java.time.LocalDate;

/**
 * The input data for the Edit Event Use Case.
 */
public class EditEventInputData {

    private final String oldTitle;
    private final String newTitle;
    private final String newDescription;
    private final LocalDate newStartDate;
    private final LocalDate newEndDate;

    public EditEventInputData(String oldTitle, String newTitle, String newDescription,
                              LocalDate newStartDate, LocalDate newEndDate) {
        this.oldTitle = oldTitle;
        this.newTitle = newTitle;
        this.newDescription = newDescription;
        this.newStartDate = newStartDate;
        this.newEndDate = newEndDate;
    }

    /**
     * Return the old title.
     * @return the old title
     */
    String getOldTitle() {
        return oldTitle;
    }

    /**
     * Return the new title.
     * @return the new title
     */
    String getNewTitle() {
        return newTitle;
    }

    /**
     * Return the new description.
     * @return the new description
     */
    String getNewDescription() {
        return newDescription;
    }

    /**
     * Return the new start date.
     * @return the new start date
     */
    LocalDate getNewStartDate() {
        return newStartDate;
    }

    /**
     * Return the new end date.
     * @return the new end date
     */
    LocalDate getNewEndDate() {
        return newEndDate;
    }
}
