package use_case.event.edit_event;

/**
 * The output data for the Edit Event Use Case.
 */
public class EditEventOutputData {

    private final boolean useCaseFailed;

    public EditEventOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
