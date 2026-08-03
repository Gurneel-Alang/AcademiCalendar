package view.event_view;

import javax.swing.*;

/**
 * Allows for viewing the Delete Event view on a separate window.
 */
public class DeleteEventDialog extends JDialog {

    public DeleteEventDialog(JFrame owner, DeleteEventView deleteEventView) {
        super(owner, "Delete Event", false);
        setContentPane(deleteEventView);
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
