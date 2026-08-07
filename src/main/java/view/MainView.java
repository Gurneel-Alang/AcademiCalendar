package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
    public MainView(
            CalendarView calendarView,
            WeatherView weatherView,
            JPanel checklistView,
            Runnable onAddEventRequested,
            Runnable onEditEventRequested,
            Runnable onDeleteEventRequested
    ) {
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
        rightContentPanel.add(checklistView, CHECKLIST_VIEW);

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

    }
    /**
     * Displays the requested feature view.
     * @param viewName name of the view
     */
    private void showView(String viewName) {
        rightCardLayout.show(rightContentPanel, viewName);
    }}