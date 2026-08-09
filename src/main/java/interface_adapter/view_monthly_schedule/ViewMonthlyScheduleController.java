package interface_adapter.view_monthly_schedule;

import java.time.YearMonth;
import use_case.view_monthly_schedule.ViewMonthlyScheduleInputBoundary;
import use_case.view_monthly_schedule.ViewMonthlyScheduleInputData;

public class ViewMonthlyScheduleController {
    private final ViewMonthlyScheduleInputBoundary interactor;
    public ViewMonthlyScheduleController(
            ViewMonthlyScheduleInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Requests the academic schedule for the selected month.
     * @param month selected month
     */
    public void execute(YearMonth month) {
        final ViewMonthlyScheduleInputData inputData =
                new ViewMonthlyScheduleInputData(month);
        interactor.execute(inputData);
    }
}
