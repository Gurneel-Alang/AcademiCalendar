package view;

import interface_adapter.add_event.AddEventController;
import interface_adapter.add_event.AddEventViewModel;

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

    private final AddEventController addEventController;
    private final AddEventViewModel addEventViewModel;

    private final JButton addEventButton;
    private final JButton cancelButton;

    private final JLabel errorLabel = new JLabel();

    public AddEventView(AddEventController addEventController,
                        AddEventViewModel addEventViewModel) {
        this.addEventController = addEventController;
        this.addEventViewModel = addEventViewModel;

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

        addEventButton = new JButton("Add");
        cancelButton = new JButton("Cancel");
        final JPanel buttons = new JPanel();
        buttons.add(addEventButton);
        buttons.add(cancelButton);

        addEventButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        if (event.getSource().equals(addEventButton)) {
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
                final Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            } else if (AddEventViewModel.ERROR_PROPERTY.equals(evt.getPropertyName())) {
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
        this.add(buttons);
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
