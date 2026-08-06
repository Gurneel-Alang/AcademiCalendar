package interface_adapter.checklist.load_checklist;

import use_case.task.load_checklist.LoadChecklistInputBoundary;
import use_case.task.load_checklist.LoadChecklistInputData;

public class LoadChecklistController {
    private final LoadChecklistInputBoundary interactor;

    public LoadChecklistController(LoadChecklistInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute(String eventId) {
        interactor.execute(new LoadChecklistInputData(eventId));
    }
}
