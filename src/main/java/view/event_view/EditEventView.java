package view.event_view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Month;

import javax.swing.*;

import interface_adapter.event.edit_event.EditEventController;
import interface_adapter.event.edit_event.EditEventViewModel;

/**
 * The view for the Edit Event Use Case.
 */
public class EditEventView extends JPanel {

    private final JTextField oldTitleInputField = new JTextField(15);
    private final JTextField newTitleInputField = new JTextField(15);
    private final JTextArea newDescriptionInputField = new JTextArea(3, 15);

    private final JComboBox<Integer> newStartYearInputField = new JComboBox<>();
    private final JComboBox<Month> newStartMonthInputField = new JComboBox<>();
    private final JComboBox<Integer> newStartDayInputField = new JComboBox<>();

    private final JComboBox<Integer> newEndYearInputField = new JComboBox<>();
    private final JComboBox<Month> newEndMonthInputField = new JComboBox<>();
    private final JComboBox<Integer> newEndDayInputField = new JComboBox<>();

    private final EditEventController editEventController;
    private final EditEventViewModel editEventViewModel;

    private final JButton editEventButton = new JButton("Edit");
    private final JButton cancelButton = new JButton("Cancel");

    public EditEventView(EditEventController editEventController, EditEventViewModel editEventViewModel) {
        this.editEventController = editEventController;
        this.editEventViewModel = editEventViewModel;

        populateDateFields();

        final JLabel viewTitle = new JLabel("Edit Event");
        viewTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel oldTitleInfo = new JPanel();
        oldTitleInfo.add(new JLabel("Enter existing title:"));
        oldTitleInfo.add(oldTitleInputField);

        final JPanel newTitleInfo = new JPanel();
        newTitleInfo.add(new JLabel("Enter new or same title:"));
        newTitleInfo.add(newTitleInputField);

        final JPanel newDescriptionInfo = new JPanel();
        newDescriptionInfo.add(new JLabel("Enter new or same description:"));
        newDescriptionInfo.add(newDescriptionInputField);

        final JPanel newStartDateFields = new JPanel();
        newStartDateFields.setLayout(new BoxLayout(newStartDateFields, BoxLayout.Y_AXIS));
        newStartDateFields.add(newStartYearInputField);
        newStartDateFields.add(newStartMonthInputField);
        newStartDateFields.add(newStartDayInputField);
        final JPanel newStartDateInfo = new JPanel();
        newStartDateInfo.add(new JLabel("Enter new or same start date:"));
        newStartDateInfo.add(newStartDateFields);

        final JPanel newEndDateFields = new JPanel();
        newEndDateFields.setLayout(new BoxLayout(newEndDateFields, BoxLayout.Y_AXIS));
        newEndDateFields.add(newEndYearInputField);
        newEndDateFields.add(newEndMonthInputField);
        newEndDateFields.add(newEndDayInputField);
        final JPanel newEndDateInfo = new JPanel();
        newEndDateInfo.add(new JLabel("Enter new or same end date:"));
        newEndDateInfo.add(newEndDateFields);

        final JPanel buttons = new JPanel();
        buttons.add(editEventButton);
        buttons.add(cancelButton);

        editEventButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(editEventButton)) {
                            final String oldTitle = oldTitleInputField.getText();
                            final String newTitle = newTitleInputField.getText();
                            final String newDescription = newDescriptionInputField.getText();
                            final LocalDate newStartDate = LocalDate.of(
                                    (Integer) newStartYearInputField.getSelectedItem(),
                                    (Month) newStartMonthInputField.getSelectedItem(),
                                    (Integer) newStartDayInputField.getSelectedItem());
                            final LocalDate newEndDate = LocalDate.of(
                                    (Integer) newEndYearInputField.getSelectedItem(),
                                    (Month) newEndMonthInputField.getSelectedItem(),
                                    (Integer) newEndDayInputField.getSelectedItem());

                            editEventController.execute(oldTitle, newTitle, newDescription,
                                    newStartDate, newEndDate);
                        }
                    }
                }
        );
        cancelButton.addActionListener(
                (ActionEvent evt) -> editEventController.switchToMainView()
        );

        editEventViewModel.addPropertyChangeListener(evt -> {
            if (EditEventViewModel.CLOSE_PROPERTY.equals(evt.getPropertyName())) {
                final Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            }
            else if (EditEventViewModel.ERROR_PROPERTY.equals(evt.getPropertyName())) {
                final String message = editEventViewModel.getState().getErrorMessage();
                JOptionPane.showMessageDialog(this, message);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(viewTitle);
        this.add(oldTitleInfo);
        this.add(newTitleInfo);
        this.add(newDescriptionInfo);
        this.add(newStartDateInfo);
        this.add(newEndDateInfo);
        this.add(buttons);
    }

    private void populateDateFields() {
        int currentYear = LocalDate.now().getYear();
        for (int y = currentYear - 3; y <= currentYear + 3; y++) {
            newStartYearInputField.addItem(y);
            newEndYearInputField.addItem(y);
        }
        for (Month m : Month.values()) {
            newStartMonthInputField.addItem(m);
            newEndMonthInputField.addItem(m);
        }
        for (int d = 1; d <= 31; d++) {
            newStartDayInputField.addItem(d);
            newEndDayInputField.addItem(d);
        }
    }
}
