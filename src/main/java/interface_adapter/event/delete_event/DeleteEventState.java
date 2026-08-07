package interface_adapter.event.delete_event;

/**
 * The state for the Delete Event view model.
 */
public class DeleteEventState {

    private String titleError;
    private String errorMessage;

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
