package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;
import com.github.lgooddatepicker.optionalusertools.CalendarListener;
import com.github.lgooddatepicker.zinternaltools.CalendarSelectionEvent;
import com.github.lgooddatepicker.zinternaltools.YearMonthChangeEvent;

public class CalendarView extends JPanel {
    private static final int CALENDAR_WIDTH = 650;
    private static final int CALENDAR_HEIGHT = 420;
    private final CalendarPanel calendarPanel;
    private boolean selectionRefreshPending;

    /**
     * Creates the calendar view.
     */
    public CalendarView() {
        setLayout(new BorderLayout(20, 0));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        final DatePickerSettings settings =
                new DatePickerSettings(Locale.CANADA);

        settings.setSizeDatePanelMinimumWidth(CALENDAR_WIDTH);
        settings.setSizeDatePanelMinimumHeight(350);
        settings.setVisibleClearButton(true);
        settings.setColor(
                DateArea.BackgroundOverallCalendarPanel,
                new Color(180, 205, 205)
        );

        calendarPanel = new CalendarPanel(settings);
        calendarPanel.setPreferredSize(
                new Dimension(CALENDAR_WIDTH, CALENDAR_HEIGHT)
        );
        calendarPanel.setSelectedDate(LocalDate.now());

        add(calendarPanel, BorderLayout.CENTER);
    }

    /**
     * Returns the currently selected date.
     * @return the selected date
     */
    public LocalDate getSelectedDate() {
        return calendarPanel.getSelectedDate();
    }

    /**
     * Returns the month currently displayed by the calendar.
     * @return the displayed month
     */
    public YearMonth getDisplayedYearMonth() {
        return calendarPanel.getDisplayedYearMonth();
    }

    /**
     * Registers a listener for date-selection changes. It also runs when the
     * displayed month changes while no date is selected.
     * @param listener callback that reloads the event list
     */
    public void addSelectionChangeListener(Runnable listener) {
        calendarPanel.addCalendarListener(new CalendarListener() {
            @Override
            public void selectedDateChanged(CalendarSelectionEvent event) {
                if (!event.isDuplicate()) {
                    scheduleSelectionRefresh(listener);
                }
            }

            @Override
            public void yearMonthChanged(YearMonthChangeEvent event) {
                if (!event.isDuplicate() && calendarPanel.getSelectedDate() == null) {
                    scheduleSelectionRefresh(listener);
                }
            }
        });
    }

    private void scheduleSelectionRefresh(Runnable listener) {
        if (!selectionRefreshPending) {
            selectionRefreshPending = true;
            SwingUtilities.invokeLater(() -> {
                selectionRefreshPending = false;
                listener.run();
            });
        }
    }
}
