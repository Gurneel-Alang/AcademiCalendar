package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import data_access.CheckListDataAccessObject;
import data_access.EventDataAccessObject;
import data_access.ReminderScheduler;

import entity.event.EventFactory;
import entity.task.CommonTaskFactory;

import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.create_task.CreateTaskController;
import interface_adapter.checklist.create_task.CreateTaskPresenter;
import interface_adapter.checklist.toggle_task.ToggleTaskController;
import interface_adapter.checklist.toggle_task.ToggleTaskPresenter;

import interface_adapter.event.add_event.AddEventController;
import interface_adapter.event.add_event.AddEventPresenter;
import interface_adapter.event.add_event.AddEventViewModel;
import interface_adapter.event.delete_event.DeleteEventController;
import interface_adapter.event.delete_event.DeleteEventPresenter;
import interface_adapter.event.delete_event.DeleteEventViewModel;

import interface_adapter.event.edit_event.EditEventController;
import interface_adapter.event.edit_event.EditEventPresenter;
import interface_adapter.event.edit_event.EditEventViewModel;
import use_case.event_use_case.add_event.AddEventInteractor;
import use_case.event_use_case.delete_event.DeleteEventInteractor;
import use_case.event_use_case.edit_event.EditEventInteractor;
import use_case.task.create_task.CreateTaskInteractor;
import use_case.task.toggle_task.ToggleTaskInteractor;

import view.ChecklistView;
import view.event_view.AddEventDialog;
import view.event_view.AddEventView;
import view.event_view.*;
import view.CalendarView;
import view.MainView;
import view.WeatherView;

/**
 * Runs a preview of the application views.
 */
public final class CalendarPreviewMain {

    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 600;

    private CalendarPreviewMain() {
    }

    /**
     * Starts the preview application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                CalendarPreviewMain::startApplication
        );
    }

    private static void startApplication() {
        final String apiKey =
                System.getenv("OPENWEATHER_API_KEY");

        final JFrame frame =
                new JFrame("AcademiCalendar");

        final CalendarView calendarView =
                new CalendarView();

        final WeatherView weatherView =
                WeatherUseCaseFactory.create(apiKey);

        final MainView mainView =
                new MainView(calendarView, weatherView);

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        frame.setContentPane(mainView);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}