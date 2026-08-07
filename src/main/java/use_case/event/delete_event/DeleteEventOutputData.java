package use_case.event.delete_event;

/**
 * The output data for the Delete Event Use Case.
 */
public class DeleteEventOutputData {

    private final boolean useCaseFailed;

    public DeleteEventOutputData(boolean useCaseFailed) {
        this.useCaseFailed = useCaseFailed;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
