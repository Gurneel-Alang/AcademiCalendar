package interface_adapter.add_event;

/**
 * The state for the Add Event view model.
 */
public class AddEventState {

    private String titleError;
    private String errorMessage;

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
