package view;

import interface_adapter.ViewManagerModel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.beans.PropertyChangeEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

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
     *
     * @param calendarView the calendar feature view
     */
    public MainView(CalendarView calendarView, Runnable onAddEventRequested,
                    Runnable onEditEventRequested, Runnable onDeleteEventRequested) {
        setLayout(new BorderLayout());

        final JButton eventButton = new JButton("Events");
        final JButton weatherButton = new JButton("Weather");
        final JButton checklistButton = new JButton("Checklist");

        final JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        navigationPanel.add(eventButton);
        navigationPanel.add(weatherButton);
        navigationPanel.add(checklistButton);

        rightCardLayout = new CardLayout();
        rightContentPanel = new JPanel(rightCardLayout);

        final JButton addEventButton = new JButton("Add Event");
        final JButton editEventButton = new JButton("Edit Event");
        final JButton deleteEventButton = new JButton("Delete Event");
        final JButton addTaskButton = new JButton("Add Task");
        final JPanel eventButtonsPanel = new JPanel();
        eventButtonsPanel.add(addEventButton);
        eventButtonsPanel.add(editEventButton);
        eventButtonsPanel.add(deleteEventButton);
        eventButtonsPanel.add(addTaskButton);

// The first page on the right; additional pages can be added later to implement other usercase
        final JPanel eventPanel = new JPanel();
        eventPanel.add(new JLabel("Events for the selected date"));

        final JPanel weatherPanel = new JPanel();
        weatherPanel.add(new JLabel(""));

        final JPanel checklistPanel = new JPanel();
        checklistPanel.add(new JLabel("Tasks"));

        rightContentPanel.add(eventPanel, EVENT_VIEW);
        rightContentPanel.add(weatherPanel, WEATHER_VIEW);
        rightContentPanel.add(checklistPanel, CHECKLIST_VIEW);

// Main area: Calendar is fixed; you can switch between views on the right
        final JPanel mainContentPanel = new JPanel(new BorderLayout());

        mainContentPanel.add(calendarView, BorderLayout.CENTER);
        mainContentPanel.add(rightContentPanel, BorderLayout.EAST);

        eventButton.addActionListener(
                event -> showView(EVENT_VIEW)
        );
        weatherButton.addActionListener(
                event -> showView(WEATHER_VIEW)
        );
        checklistButton.addActionListener(
                event -> showView(CHECKLIST_VIEW)
        );
        addEventButton.addActionListener(
                event -> onAddEventRequested.run()
        );
        editEventButton.addActionListener(
                event -> onEditEventRequested.run()
        );
        deleteEventButton.addActionListener(
                event -> onDeleteEventRequested.run()
        );
        addTaskButton.addActionListener(
                event -> onAddEventRequested.run()
        );

        add(navigationPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
        add(eventButtonsPanel, BorderLayout.SOUTH);
    }

    /**
     * Displays the requested feature view.
     * @param viewName name of the view
     */
    private void showView(String viewName) {
        rightCardLayout.show(rightContentPanel, viewName);
    }
}