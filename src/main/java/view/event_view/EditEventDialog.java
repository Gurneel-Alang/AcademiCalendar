package view.event_view;

import javax.swing.*;

/**
 * Allows for viewing the Edit Event view on a separate window.
 */
public class EditEventDialog extends JDialog {

    public EditEventDialog(JFrame owner, EditEventView editEventView) {
        super(owner, "Edit Event", false);
        setContentPane(editEventView);
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
