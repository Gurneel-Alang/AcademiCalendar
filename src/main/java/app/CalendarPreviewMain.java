package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
            final JFrame frame =
                    new JFrame("AcademiCalendar");

            final CalendarView calendarView =
                    new CalendarView();

            final MainView mainView =
                    new MainView(calendarView);

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