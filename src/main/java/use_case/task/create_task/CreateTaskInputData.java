package use_case.task.create_task;

public class CreateTaskInputData {
    private final String description;

    public CreateTaskInputData(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
