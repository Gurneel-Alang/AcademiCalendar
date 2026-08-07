package interface_adapter.checklist.load_checklist;

import use_case.task.load_checklist.LoadChecklistInputBoundary;

public class LoadChecklistController {
    private final LoadChecklistInputBoundary interactor;

    public LoadChecklistController(LoadChecklistInputBoundary interactor){
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}
