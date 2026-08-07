package use_case.event.delete_event;

/**
 * The input data for the Delete Event Use Case.
 */
public class DeleteEventInputData {

    private final String title;

    public DeleteEventInputData(String title) {
        this.title = title;
    }

    /**
     * Return the title.
     * @return the title
     */
    String getTitle() {
        return title;
    }
}
