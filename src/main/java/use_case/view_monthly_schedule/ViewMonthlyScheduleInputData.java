package use_case.view_monthly_schedule;
import java.time.YearMonth;

public class ViewMonthlyScheduleInputData {
    private final YearMonth month;

    public ViewMonthlyScheduleInputData(YearMonth month) {
        this.month = month;
    }

    public YearMonth getMonth() {
        return month;
    }
}