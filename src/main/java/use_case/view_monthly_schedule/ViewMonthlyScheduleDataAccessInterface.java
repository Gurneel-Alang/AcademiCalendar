package use_case.view_monthly_schedule;

import java.time.YearMonth;
import java.util.List;

import entity.event.EventInterface;

/**
 * Data access interface for viewing events in a selected month.
 */
public interface ViewMonthlyScheduleDataAccessInterface {

    /**
     * Returns all events that occur in the selected month.
     *
     * @param month the selected month
     * @return the events occurring in that month
     */
    List<EventInterface> getEventsForMonth(YearMonth month);
}
