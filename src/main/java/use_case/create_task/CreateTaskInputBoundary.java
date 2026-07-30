package use_case.create_task;

/**
 * Input boundary for the Create Task use case
 */
public interface CreateTaskInputBoundary{
    void execute(CreateTaskInputData inputData);
}