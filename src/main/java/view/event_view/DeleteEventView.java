package view.event_view;

import interface_adapter.event.delete_event.DeleteEventController;
import interface_adapter.event.delete_event.DeleteEventViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The view for the Delete Event Use Case.
 */
public class DeleteEventView extends JPanel {

    private final JTextField titleInputField = new JTextField(15);

    private final DeleteEventController deleteEventController;
    private final DeleteEventViewModel deleteEventViewModel;

    private final JButton deleteEventButton = new JButton("Delete");
    private final JButton cancelButton = new JButton("Cancel");

    public DeleteEventView(DeleteEventController deleteEventController, DeleteEventViewModel deleteEventViewModel) {
        this.deleteEventController = deleteEventController;
        this.deleteEventViewModel = deleteEventViewModel;

        final JLabel viewTitle = new JLabel("Delete Event");
        viewTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel titleInfo = new JPanel();
        titleInfo.add(new JLabel("Enter title:"));
        titleInfo.add(titleInputField);

        final JPanel buttons = new JPanel();
        buttons.add(deleteEventButton);
        buttons.add(cancelButton);

        deleteEventButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(deleteEventButton)) {
                            final String title = titleInputField.getText();

                            deleteEventController.execute(title);
                        }
                    }
                }
        );
        cancelButton.addActionListener(
                (ActionEvent evt) -> deleteEventController.switchToMainView()
        );

        deleteEventViewModel.addPropertyChangeListener(evt -> {
            if (DeleteEventViewModel.CLOSE_PROPERTY.equals(evt.getPropertyName())) {
                final Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.dispose();
                }
            } else if (DeleteEventViewModel.ERROR_PROPERTY.equals(evt.getPropertyName())) {
                final String message = deleteEventViewModel.getState().getErrorMessage();
                JOptionPane.showMessageDialog(this, message);
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(viewTitle);
        this.add(titleInfo);
        this.add(buttons);
    }
}
