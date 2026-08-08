package use_case.task.create_task;
import java.time.LocalDate;

public class CreateTaskInputData {
    private final String description;
    private final LocalDate dueDate;

    public CreateTaskInputData(String description, LocalDate dueDate) {
        this.description = description;
        this.dueDate = dueDate;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDueDate(){
        return dueDate;}
}
