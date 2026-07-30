package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;
import java.awt.Dimension;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


import com.github.lgooddatepicker.components.CalendarPanel;
import com.github.lgooddatepicker.components.DatePickerSettings;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;


public class CalendarView extends JPanel {
    private static final int CALENDAR_WIDTH = 650;
    private static final int CALENDAR_HEIGHT = 420;
    private final CalendarPanel calendarPanel;

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
     *
     * @return the selected date
     */
    public LocalDate getSelectedDate() {
        return calendarPanel.getSelectedDate();
    }
}