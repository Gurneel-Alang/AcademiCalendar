package use_case.event.add_event;

/**
 * The output data for the Add Event Use Case.
 */
public class AddEventOutputData {

    private final boolean useCaseFailed;

    public AddEventOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
