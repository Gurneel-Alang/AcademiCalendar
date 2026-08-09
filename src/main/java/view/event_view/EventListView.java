package view.event_view;

import java.awt.*;
import java.util.List;

import javax.swing.*;

import java.beans.PropertyChangeListener;

import interface_adapter.event.view_events.EventSummary;
import interface_adapter.event.view_events.ViewEventsViewModel;

public class EventListView extends JPanel {

    private final JPanel stackedEventsPanel;

    public EventListView(ViewEventsViewModel viewEventsViewModel) {
        setLayout(new BorderLayout());
        stackedEventsPanel = new JPanel();
        stackedEventsPanel.setLayout(new BoxLayout(stackedEventsPanel, BoxLayout.Y_AXIS));

        final JScrollPane scrollPane = new JScrollPane(stackedEventsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);

        render(viewEventsViewModel.getState().getEvents());

        final PropertyChangeListener listener = evt -> {
            if (ViewEventsViewModel.EVENTS_PROPERTY.equals(evt.getPropertyName())) {
                render(viewEventsViewModel.getState().getEvents());
            }
        };
        viewEventsViewModel.addPropertyChangeListener(listener);
    }

    private void render(List<EventSummary> events) {
        stackedEventsPanel.removeAll();

        if (events.isEmpty()) {
            final JLabel emptyLabel = new JLabel("No events were found.");
            emptyLabel.setHorizontalAlignment(SwingConstants.LEFT);
            emptyLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 15, 15, 15));
            stackedEventsPanel.add(emptyLabel);
        }
        else {
            for (EventSummary event : events) {
                final EventDetailsView eventDetailsView = new EventDetailsView();
                eventDetailsView.displayEvent(event);
                stackedEventsPanel.add(eventDetailsView);
            }
        }

        stackedEventsPanel.revalidate();
        stackedEventsPanel.repaint();
    }
}
