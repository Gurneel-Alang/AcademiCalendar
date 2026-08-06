package interface_adapter.event.edit_event;

/**
 * The state for the Edit Event view model.
 */
public class EditEventState {

    private String oldTitleError;
    private String newTitleError;
    private String errorMessage;

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
