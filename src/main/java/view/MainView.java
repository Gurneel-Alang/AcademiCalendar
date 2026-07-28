package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Main application view containing navigation and feature views.
 */
public class MainView extends JPanel {
    private static final String EVENT_VIEW = "event";
    private static final String WEATHER_VIEW = "weather";

    private final CardLayout rightCardLayout;
    private final JPanel rightContentPanel;

    /**
     * Creates the main application view.
     *
     * @param calendarView the calendar feature view
     */
    public MainView(
            CalendarView calendarView) {
        setLayout(new BorderLayout());

        final JButton eventButton =
                new JButton("Events");
        final JButton weatherButton =
                new JButton("Weather");

        final JPanel navigationPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        navigationPanel.add(eventButton);
        navigationPanel.add(weatherButton);

        rightCardLayout = new CardLayout();
        rightContentPanel = new JPanel(rightCardLayout);

// The first page on the right; additional pages can be added later to implement other usercase
        final JPanel eventPanel = new JPanel();
        eventPanel.add(new JLabel("Events for the selected date"));

        final JPanel weatherPanel = new JPanel();
        weatherPanel.add(new JLabel(""));

        rightContentPanel.add(eventPanel, EVENT_VIEW);
        rightContentPanel.add(weatherPanel, WEATHER_VIEW);

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
        add(navigationPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
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