package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import data_access.EventDataAccessObject;
import data_access.ReminderScheduler;
import entity.event.EventFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_event.AddEventController;
import interface_adapter.add_event.AddEventPresenter;
import interface_adapter.add_event.AddEventViewModel;
import use_case.event_use_case.add_event.AddEventInteractor;
import view.AddEventDialog;
import view.AddEventView;
import view.CalendarView;
import view.MainView;

/**
 * Runs a preview of the application views.
 */
public class CalendarPreviewMain {

    /**
     * Starts the preview application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final JFrame frame = new JFrame("AcademiCalendar");

            final EventDataAccessObject eventDataAccessObject = new EventDataAccessObject();
            final AddEventViewModel addEventViewModel = new AddEventViewModel();
            final AddEventPresenter addEventPresenter = new AddEventPresenter(addEventViewModel);
            final AddEventInteractor addEventInteractor = new AddEventInteractor(
                    eventDataAccessObject, addEventPresenter, new EventFactory());
            final AddEventController addEventController = new AddEventController(addEventInteractor);
            final ReminderScheduler reminderScheduler = new ReminderScheduler();
            final AddEventView addEventView = new AddEventView(addEventController, addEventViewModel, reminderScheduler);

            final CalendarView calendarView = new CalendarView();
            final MainView mainView = new MainView(calendarView, () -> {
                final AddEventDialog dialog = new AddEventDialog(frame, addEventView);
                dialog.setVisible(true); // blocks here (modal) until dialog.dispose() is called
            });

            frame.setDefaultCloseOperation(
                    JFrame.EXIT_ON_CLOSE
            );
            frame.setContentPane(mainView);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}