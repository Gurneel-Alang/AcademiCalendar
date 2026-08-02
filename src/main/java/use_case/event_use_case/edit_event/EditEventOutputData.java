package use_case.event_use_case.edit_event;

/**
 * The output data for the Edit Event Use Case.
 */
public class EditEventOutputData {

    public final boolean useCaseFailed;

    public EditEventOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
