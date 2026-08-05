package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import data_access.EventDataAccessObject;
import entity.event.EventFactory;
import interface_adapter.event.add_event.AddEventController;
import interface_adapter.event.add_event.AddEventPresenter;
import interface_adapter.event.add_event.AddEventViewModel;
import interface_adapter.event.delete_event.DeleteEventController;
import interface_adapter.event.delete_event.DeleteEventPresenter;
import interface_adapter.event.delete_event.DeleteEventViewModel;
import use_case.event_use_case.add_event.AddEventInteractor;
import use_case.event_use_case.delete_event.DeleteEventInteractor;
import view.event_view.AddEventDialog;
import view.event_view.AddEventView;
import view.CalendarView;
import view.MainView;
import view.event_view.DeleteEventDialog;
import view.event_view.DeleteEventView;

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
            final AddEventView addEventView = new AddEventView(addEventController, addEventViewModel);

            final DeleteEventViewModel deleteEventViewModel = new DeleteEventViewModel();
            final DeleteEventPresenter deleteEventPresenter = new DeleteEventPresenter(deleteEventViewModel);
            final DeleteEventInteractor deleteEventInteractor = new DeleteEventInteractor(
                    eventDataAccessObject, deleteEventPresenter);
            final DeleteEventController deleteEventController = new DeleteEventController(deleteEventInteractor);
            final DeleteEventView deleteEventView = new DeleteEventView(deleteEventController, deleteEventViewModel);

            final CalendarView calendarView = new CalendarView();
            final MainView mainView = new MainView(calendarView,
                    () -> {
                final AddEventDialog dialog = new AddEventDialog(frame, addEventView);
                dialog.setVisible(true);
                }, () -> {
                final DeleteEventDialog dialog = new DeleteEventDialog(frame, deleteEventView);
                dialog.setVisible(true);
            });

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(mainView);
            frame.setSize(1000, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}