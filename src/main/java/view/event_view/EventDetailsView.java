package view.event_view;

import java.awt.*;

import javax.swing.*;

import interface_adapter.event.view_events.EventSummary;

public class EventDetailsView extends JPanel {

    private final JLabel eventTitleLabel = new JLabel();
    private final JTextArea eventDescriptionLabel = new JTextArea();
    private final JLabel eventDurationLabel = new JLabel();

    public EventDetailsView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        eventTitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventDescriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        eventDurationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        eventDescriptionLabel.setEditable(false);
        eventDescriptionLabel.setFocusable(false);
        eventDescriptionLabel.setLineWrap(true);
        eventDescriptionLabel.setWrapStyleWord(true);
        eventDescriptionLabel.setOpaque(false);

        final JPanel informationPanel = new JPanel();
        informationPanel.setLayout(
                new javax.swing.BoxLayout(informationPanel, javax.swing.BoxLayout.Y_AXIS)
        );
        informationPanel.add(eventTitleLabel);
        informationPanel.add(eventDescriptionLabel);
        informationPanel.add(eventDurationLabel);

        add(informationPanel, BorderLayout.NORTH);
    }

    /**
     * Changes this view to display a particular event.
     * @param event the event to display
     */
    public void displayEvent(EventSummary event) {
        if (event == null) {
            eventTitleLabel.setText("");
            eventDescriptionLabel.setText("");
            eventDurationLabel.setText("");
            return;
        }

        if ("".equals(event.getTitle())) {
            eventTitleLabel.setText("No title provided.");
        }
        else {
            eventTitleLabel.setText("Title: " + event.getTitle());
        }

        if ("".equals(event.getDescription())) {
            eventDescriptionLabel.setText("No description provided.");
        }
        else {
            eventDescriptionLabel.setText("Description: " + event.getDescription());
        }

        if (event.getStartDate().isEqual(event.getEndDate())) {
            eventDurationLabel.setText("Date(s): " + event.getStartDate().toString());
        }
        else {
            eventDurationLabel.setText("Dates: " + event.getStartDate() + " - " + event.getEndDate());
        }

        revalidate();
        repaint();
    }
}
