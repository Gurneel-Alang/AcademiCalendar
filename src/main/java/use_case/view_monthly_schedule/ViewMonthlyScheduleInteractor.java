package use_case.view_monthly_schedule;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entity.event.EventInterface;

/**
 * Interactor for viewing a monthly schedule.
 */
public class ViewMonthlyScheduleInteractor
        implements ViewMonthlyScheduleInputBoundary {

    private final ViewMonthlyScheduleDataAccessInterface dataAccessObject;
    private final ViewMonthlyScheduleOutputBoundary presenter;

    public ViewMonthlyScheduleInteractor(
            ViewMonthlyScheduleDataAccessInterface dataAccessObject,
            ViewMonthlyScheduleOutputBoundary presenter) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void execute(ViewMonthlyScheduleInputData inputData) {
        if (inputData == null || inputData.getMonth() == null) {
            presenter.prepareFailView("A month must be selected.");
            return;
        }

        final YearMonth month = inputData.getMonth();
        final List<EventInterface> events =
                dataAccessObject.getEventsForMonth(month);

        final List<ViewMonthlyScheduleOutputData.MonthlyEventData> eventData =
                new ArrayList<>();

        for (EventInterface event : events) {
            final LocalDate startDate = event.getStartDate();

            final LocalDate endDate = event.getEndDate();

            eventData.add(
                    new ViewMonthlyScheduleOutputData.MonthlyEventData(
                            event.getId(),
                            event.getTitle(),
                            event.getDescription(),
                            startDate,
                            endDate
                    )
            );
        }

        final ViewMonthlyScheduleOutputData outputData =
                new ViewMonthlyScheduleOutputData(month, eventData);
        presenter.prepareSuccessView(outputData);
    }

    private LocalDate convertToLocalDate(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
}
