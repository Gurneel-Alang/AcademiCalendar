package interface_adapter.reminder.schedule_reminder;

import use_case.reminder.schedule_reminder.ScheduleReminderOutputBoundary;
import use_case.reminder.schedule_reminder.ScheduleReminderOutputData;

/**
 * Presenter getting ready to schedule upcoming reminders, preparing for both success and fail cases.
 */
public class ScheduleReminderPresenter implements ScheduleReminderOutputBoundary {

    private final ScheduleReminderViewModel viewModel;

    public ScheduleReminderPresenter(ScheduleReminderViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(ScheduleReminderOutputData outputData) {
        final ScheduleReminderState state = viewModel.getState();
        state.setConfirmationMessage("Reminder set for \"" + outputData.getEventTitle()
                + "\" will remind in " + outputData.getSecondsUntilFire() + " seconds.");
        viewModel.firePropertyChanged(ScheduleReminderViewModel.CONFIRM_PROPERTY);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final ScheduleReminderState state = viewModel.getState();
        state.setConfirmationMessage(errorMessage);
        viewModel.firePropertyChanged(ScheduleReminderViewModel.CONFIRM_PROPERTY);
    }

    @Override
    public void reminderFired(ScheduleReminderOutputData outputData) {
        final ScheduleReminderState state = viewModel.getState();
        state.setFiredEventTitle(outputData.getEventTitle());
        state.setFiredPastLabel(outputData.getPastLabel());
        viewModel.firePropertyChanged(ScheduleReminderViewModel.FIRED_PROPERTY);
    }
}
