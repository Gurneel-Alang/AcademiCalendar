package use_case.reminder.schedule_reminder;

import entity.reminder.ReminderChoices;

/**
 * The Schedule Reminder use case interactor. The Interactor validate the input,
 * then schedule through the DAI, and output the outcome through the output boundary.
 */
public class ScheduleReminderInteractor implements ScheduleReminderInputBoundary {

    private final ScheduleReminderDataAccessInterface scheduler;
    private final ScheduleReminderOutputBoundary presenter;

    public ScheduleReminderInteractor(ScheduleReminderDataAccessInterface scheduler,
                                      ScheduleReminderOutputBoundary presenter) {
        this.scheduler = scheduler;
        this.presenter = presenter;
    }

    @Override
    public void execute(ScheduleReminderInputData inputData) {
        final String eventTitle = inputData.getEventTitle();
        final ReminderChoices option = inputData.getOption();

        if (eventTitle == null || eventTitle.isBlank()) {
            presenter.prepareFailView("A reminder needs an event title.");
            return;
        }
        if (option == null) {
            presenter.prepareFailView("No reminder option was selected.");
            return;
        }

        final long secondsUntilFire = scheduler.schedule(eventTitle, option,
                (firedTitle, firedOption) -> presenter.reminderFired(
                        new ScheduleReminderOutputData(
                                firedTitle,
                                firedOption.getLabel(),
                                firedOption.getPastLabel(),
                                0)));

        presenter.prepareSuccessView(new ScheduleReminderOutputData(
                eventTitle,
                option.getLabel(),
                option.getPastLabel(),
                secondsUntilFire));
    }
}
