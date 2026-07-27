package use_case.view_monthly_schedule;

/**
 * Output boundary for the View Monthly Schedule use case.
 */
public interface ViewMonthlyScheduleOutputBoundary {

    /**
     * Prepares the monthly schedule for display.
     *
     * @param outputData the schedule data
     */
    void prepareSuccessView(ViewMonthlyScheduleOutputData outputData);

    /**
     * Prepares an error message for display.
     *
     * @param errorMessage the error message
     */
    void prepareFailView(String errorMessage);
}