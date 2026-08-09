package interface_adapter.view_monthly_schedule;

import java.util.Comparator;
import java.util.List;

import interface_adapter.event.view_events.EventSummary;
import interface_adapter.event.view_events.ViewEventsState;
import interface_adapter.event.view_events.ViewEventsViewModel;
import use_case.view_monthly_schedule.ViewMonthlyScheduleOutputBoundary;
import use_case.view_monthly_schedule.ViewMonthlyScheduleOutputData;

public class ViewMonthlySchedulePresenter
        implements ViewMonthlyScheduleOutputBoundary {
    private final ViewEventsViewModel viewModel;

    public ViewMonthlySchedulePresenter(
            ViewEventsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ViewMonthlyScheduleOutputData outputData) {
        final ViewEventsState state = viewModel.getState();
        state.setEvents(outputData.getEvents().stream()
                .sorted(Comparator.comparing(
                        ViewMonthlyScheduleOutputData.MonthlyEventData::getStartDate)
                        .thenComparing(
                                ViewMonthlyScheduleOutputData.MonthlyEventData::getTitle))
                .map(event -> new EventSummary(
                        event.getTitle(), event.getDescription(),
                        event.getStartDate(), event.getEndDate()))
                .toList());
        state.setErrorMessage("");
        viewModel.firePropertyChanged(ViewEventsViewModel.EVENTS_PROPERTY);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ViewEventsState state = viewModel.getState();
        state.setEvents(List.of());
        state.setErrorMessage(errorMessage);
        viewModel.firePropertyChanged(ViewEventsViewModel.EVENTS_PROPERTY);
    }
}
