package view;

import javax.swing.*;

/**
 * Allows for viewing the Add Event view on a separate window.
 */
public class AddEventDialog extends JDialog {

    public AddEventDialog(JFrame owner, AddEventView addEventView) {
        super(owner, "Add Event", false);
        setContentPane(addEventView);
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
}
