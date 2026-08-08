package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.*;

import interface_adapter.checklist.create_task.CreateTaskController;
import interface_adapter.checklist.load_checklist.LoadChecklistController;
import use_case.task.load_checklist.LoadChecklistOutputBoundary;

/**
 * Main application view containing navigation and feature views.
 */
public class MainView extends JPanel {
    private static final String EVENT_VIEW = "event";
    private static final String WEATHER_VIEW = "weather";
    private static final String CHECKLIST_VIEW = "checklist";

    private final CardLayout rightCardLayout;
    private final JPanel rightContentPanel;

    /**
     * Creates the main application view.
     * @param calendarView the calendar view
     * @param weatherView the weather view
     * @param checklistView the checklist view
     * @param onAddEventRequested the thread for the add event dialog
     * @param onEditEventRequested the thread for the edit event dialog
     * @param onDeleteEventRequested the thread for the delete event dialog
     */
    public MainView(CalendarView calendarView, WeatherView weatherView,
                    JPanel checklistView, CreateTaskController createTaskController,
                    LoadChecklistController loadChecklistController,
                    Runnable onAddEventRequested,
                    Runnable onEditEventRequested, Runnable onDeleteEventRequested) {
        setLayout(new BorderLayout());

        final JButton eventButton = new JButton("Events");
        final JButton weatherButton = new JButton("Weather");
        final JButton checklistButton = new JButton("Checklist");

        final JPanel navigationPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        navigationPanel.add(eventButton);
        navigationPanel.add(weatherButton);
        navigationPanel.add(checklistButton);

        rightCardLayout = new CardLayout();
        rightContentPanel = new JPanel(rightCardLayout);

        /*
         * Temporary event and weather panels.
         */
        final JPanel eventPanel = new JPanel();
        eventPanel.add(new JLabel("Events for the selected date"));

        rightContentPanel.add(eventPanel, EVENT_VIEW);
        rightContentPanel.add(weatherView, WEATHER_VIEW);

        /*
         * Checklist view + task input, wrapped together so the
         * "Checklist" nav card includes both.
         */
        final JPanel checklistPanel = new JPanel(new BorderLayout());
        checklistPanel.add(checklistView, BorderLayout.CENTER);

        final JTextField taskInput = new JTextField(20);
        final JButton addTaskButton = new JButton("Add Task");

        final JPanel addTaskPanel = new JPanel(new FlowLayout());
        addTaskPanel.add(taskInput);
        addTaskPanel.add(addTaskButton);
        checklistPanel.add(addTaskPanel, BorderLayout.SOUTH);

        rightContentPanel.add(checklistPanel, CHECKLIST_VIEW);


        final JPanel mainContentPanel = new JPanel(new BorderLayout());

        mainContentPanel.add(calendarView, BorderLayout.CENTER);
        mainContentPanel.add(rightContentPanel, BorderLayout.EAST);

        /*
         * Event buttons.
         */
        final JButton addEventButton = new JButton("Add Event");
        final JButton editEventButton = new JButton("Edit Event");
        final JButton deleteEventButton = new JButton("Delete Event");

        final JPanel eventButtonsPanel = new JPanel();
        eventButtonsPanel.add(addEventButton);
        eventButtonsPanel.add(editEventButton);
        eventButtonsPanel.add(deleteEventButton);

        /*
         * Navigation
         */
        eventButton.addActionListener(
                event -> showView(EVENT_VIEW)
        );

        weatherButton.addActionListener(event -> {
            weatherView.setSelectedDate(
                    calendarView.getSelectedDate()
            );

            showView(WEATHER_VIEW);
        });

        checklistButton.addActionListener(
                event -> showView(CHECKLIST_VIEW)
        );

        /*
         * Checklist task creation.
         */
        addTaskButton.addActionListener(event -> {
            final String description = taskInput.getText();
            if (!description.isBlank()) {
                createTaskController.execute(description);
                taskInput.setText("");
            }
        });
        taskInput.addActionListener(event -> {
            final String description = taskInput.getText();
            if (!description.isBlank()) {
                createTaskController.execute(description);
                taskInput.setText("");
            }
        });

        /*
         * Event use cases.
         */
        addEventButton.addActionListener(
                event -> onAddEventRequested.run()
        );
        editEventButton.addActionListener(
                event -> onEditEventRequested.run()
        );
        deleteEventButton.addActionListener(
                event -> onDeleteEventRequested.run()
        );

        add(navigationPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
        add(eventButtonsPanel, BorderLayout.SOUTH);

        showView(EVENT_VIEW);
    }

    /**
     * Displays the requested feature view.
     *
     * @param viewName name of the view
     */
    private void showView(String viewName) {
        rightCardLayout.show(rightContentPanel, viewName);
    }
}
