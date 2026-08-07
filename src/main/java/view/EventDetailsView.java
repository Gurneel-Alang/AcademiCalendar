package view;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventDetailsView extends JPanel {

    private final JLabel eventTitleLabel = new JLabel();
    private final JLabel eventDateLabel = new JLabel();

    public EventDetailsView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        final JPanel informationPanel = new JPanel();
        informationPanel.setLayout(
                new javax.swing.BoxLayout(informationPanel, javax.swing.BoxLayout.Y_AXIS)
        );
        informationPanel.add(eventTitleLabel);
        informationPanel.add(eventDateLabel);

        add(informationPanel, BorderLayout.NORTH);
    }

    public void displayEvent(String eventId, String eventTitle, String eventDate) {
        eventTitleLabel.setText("Event: " + eventTitle);
        eventDateLabel.setText("Date: " + eventDate);
    }
}