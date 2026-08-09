package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import view.MainView;

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
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(
                CalendarPreviewMain::startApplication
        );
    }

    private static void startApplication() {
        final JFrame frame = new JFrame("AcademiCalendar");
        final MainViewBuilder mainViewBuilder = new MainViewBuilder(frame);
        final MainView mainView = mainViewBuilder.addCalendarView()
                        .addWeatherView()
                            .addEventListView()
                                .addCheckListView()
                                    .addStudyTimerView()
                                        .addAddEventView()
                                            .addEditEventView()
                                                .addDeleteEventView()
                                                    .build();

        frame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        frame.setContentPane(mainView);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
