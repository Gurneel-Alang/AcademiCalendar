package view.event_view;

import data_access.ReminderScheduler;
import entity.reminder.ReminderChoices;
import interface_adapter.event.add_event.AddEventController;
import interface_adapter.event.add_event.AddEventViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

/**
 * The view for the Add Event Use Case.
 */
public class AddEventView extends JPanel {

    private final JTextField titleInputField = new JTextField(15);
    private final JTextArea descriptionInputField = new JTextArea(3, 15);

    private final JComboBox<Integer> startYearInputField = new JComboBox<>();
    private final JComboBox<Month> startMonthInputField = new JComboBox<>();
    private final JComboBox<Integer> startDayInputField = new JComboBox<>();

    private final JComboBox<Integer> endYearInputField = new JComboBox<>();
    private final JComboBox<Month> endMonthInputField = new JComboBox<>();
    private final JComboBox<Integer> endDayInputField = new JComboBox<>();

    // Added Reminder setup for AddEvent here

    private final JComboBox<ReminderChoices> reminderInputField =
            new JComboBox<>(ReminderChoices.all());

    private final AddEventController addEventController;
    private final AddEventViewModel addEventViewModel;
    private final ReminderScheduler reminderScheduler;

    private String pendingReminderTitle;
    private ReminderChoices pendingReminderOption;

    private final JButton  addEventButton = new JButton("Add");
    private final JButton cancelButton = new JButton("Cancel");

    private final JLabel errorLabel = new JLabel();

    public AddEventView(AddEventController addEventController,
                        AddEventViewModel addEventViewModel,
                        ReminderScheduler reminderScheduler) {
        this.addEventController = addEventController;
        this.addEventViewModel = addEventViewModel;
        this.reminderScheduler = reminderScheduler;

        populateDateFields();

        final JLabel viewTitle = new JLabel("Add Event");
        viewTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel titleInfo = new JPanel();
        titleInfo.add(new JLabel("Enter title:"));
        titleInfo.add(titleInputField);

        final JPanel descriptionInfo = new JPanel();
        descriptionInfo.add(new JLabel("Enter description:"));
        descriptionInfo.add(descriptionInputField);

        final JPanel startDateFields = new JPanel();
        startDateFields.setLayout(new BoxLayout(startDateFields, BoxLayout.Y_AXIS));
        startDateFields.add(startYearInputField);
        startDateFields.add(startMonthInputField);
        startDateFields.add(startDayInputField);
        final JPanel startDateInfo = new JPanel();
        startDateInfo.add(new JLabel("Enter start date:"));
        startDateInfo.add(startDateFields);

        final JPanel endDateFields = new JPanel();
        endDateFields.setLayout(new BoxLayout(endDateFields, BoxLayout.Y_AXIS));
        endDateFields.add(endYearInputField);
        endDateFields.add(endMonthInputField);
        endDateFields.add(endDayInputField);
        final JPanel endDateInfo = new JPanel();
        endDateInfo.add(new JLabel("Enter end date:"));
        endDateInfo.add(endDateFields);

        // Added Reminder setup here
        final JPanel reminderInfo = new JPanel();
        reminderInfo.add(new JLabel("Remind me:"));
        reminderInfo.add(reminderInputField);


        final JPanel buttons = new JPanel();
        buttons.add(addEventButton);
        buttons.add(cancelButton);

        addEventButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addEventButton)) {
                            final String title = titleInputField.getText();
                            final String description = descriptionInputField.getText();
                            final LocalDate startDate = LocalDate.of(
                                    (Integer) startYearInputField.getSelectedItem(),
                                    (Month) startMonthInputField.getSelectedItem(),
                                    (Integer) startDayInputField.getSelectedItem());
                            final LocalDate endDate = LocalDate.of(
                                    (Integer) endYearInputField.getSelectedItem(),
                                    (Month) endMonthInputField.getSelectedItem(),
                                    (Integer) endDayInputField.getSelectedItem());

                            // Added reminders schedule queue
                            pendingReminderTitle = title;
                            pendingReminderOption = (ReminderChoices) reminderInputField.getSelectedItem();

                            addEventController.execute(title, description, startDate, endDate);
                        }
                    }
                }
        );
        cancelButton.addActionListener(
                (ActionEvent evt) -> addEventController.switchToMainView()
        );

        addEventViewModel.addPropertyChangeListener(evt -> {
            if (AddEventViewModel.CLOSE_PROPERTY.equals(evt.getPropertyName())) {
                if (pendingReminderTitle != null){
                    reminderScheduler.schedule(
                            pendingReminderTitle,
                            pendingReminderOption,
                            this::showReminder);
                    pendingReminderTitle = null;
                    pendingReminderOption = null;
                }
                final Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            } else if (AddEventViewModel.ERROR_PROPERTY.equals(evt.getPropertyName())) {
                pendingReminderTitle = null;
                pendingReminderOption = null;
                final String message = addEventViewModel.getState().getErrorMessage();
                JOptionPane.showMessageDialog(this, message);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(viewTitle);
        this.add(titleInfo);
        this.add(descriptionInfo);
        this.add(startDateInfo);
        this.add(endDateInfo);
        this.add(reminderInfo);
        this.add(buttons);
    }

    private void showReminder(String eventTitle, ReminderChoices option) {
        final Object[] buttons = {"Remind me again in 1 hour", "Dismiss"};
        final int choice = JOptionPane.showOptionDialog(
                null,
                "Reminder: \"" + eventTitle + "\" (" + option.getPastLabel() + ")",  // message
                "Reminder",                                                            // title
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                buttons,
                buttons[0]);
        if (choice == 0) {
            reminderScheduler.schedule(eventTitle, ReminderChoices.oneHour(), this::showReminder);
        }
    }

    private void populateDateFields() {
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 3; y <= currentYear + 3; y++) {
            startYearInputField.addItem(y);
            endYearInputField.addItem(y);
        }
        for (Month m : Month.values()) {
            startMonthInputField.addItem(m);
            endMonthInputField.addItem(m);
        }
        for (int d = 1; d <= 31; d++) {
            startDayInputField.addItem(d);
            endDayInputField.addItem(d);
        }
    }
}
