package interface_adapter.checklist;

public class TaskState {
    private final String id;
    private final String description;
    private final boolean completed;

    public TaskState(
            String id,
            String description,
            boolean completed
    ) {
        this.id = id;
        this.description = description;
        this.completed = completed;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public boolean isCompleted() {
        return completed;
    }
}
