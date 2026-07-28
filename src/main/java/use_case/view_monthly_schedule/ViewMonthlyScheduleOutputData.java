package use_case.view_monthly_schedule;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

/**
 * Output data for viewing a monthly schedule.
 */
public class ViewMonthlyScheduleOutputData {

    private final YearMonth month;
    private final List<MonthlyEventData> events;

    public ViewMonthlyScheduleOutputData(
            YearMonth month,
            List<MonthlyEventData> events) {
        this.month = month;
        this.events = events;
    }

    public YearMonth getMonth() {
        return month;
    }

    public List<MonthlyEventData> getEvents() {
        return events;
    }



    /**
     * Data for one event displayed in the monthly calendar.
     */
    public static class MonthlyEventData {

        private final int id;
        private final String title;
        private final String description;
        private final LocalDate startDate;
        private final LocalDate endDate;

        public MonthlyEventData(
                int id,
                String title,
                String description,
                LocalDate startDate,
                LocalDate endDate) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }
    }
}