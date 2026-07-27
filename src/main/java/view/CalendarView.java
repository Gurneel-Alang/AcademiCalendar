package view;

import java.swing.*;
import com.github.lgooddatepicker.components.DatePickerSettings.DateArea;

public class CalendarView {
    private final JFrame border = newJFrame();

    // Import calendar, and set coloring
    DatePickerSettings calendarImport = new DatePickerSettings();
    Color bgColor = new Color (180, 205, 205);
    calendarImport.setColor(DateArea.BackgroundOverallCalendarPanel, bgColor);

    // Set selected date as highlighted
    calendarImport.sethighlightPolicy (new EventHighlightPolicy());

    // Might need this for further events, delete if unnecessary
    calendarPanel = new calendarPanel(dateSettings);
    calendarPanel.setSelectedDate(LocalDate.now());

    // Panel to hold the calendar
    panel = new JPanel();
    panel.add(calendarPanel, BorderLayout.NORTH)
}