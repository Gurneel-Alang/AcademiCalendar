package interface_adapter.event.view_events;

import entity.event.EventInterface;

import java.time.LocalDate;
import java.util.List;

/**
 * The controller for viewing events on a given date. Skips the use case layer.
 */
public class ViewEventsController {

    private final EventsGateway eventsGateway;
    private ViewEventsViewModel viewEventsViewModel;

    public ViewEventsController(EventsGateway eventsGateway, ViewEventsViewModel viewEventsViewModel) {
        this.eventsGateway = eventsGateway;
        this.viewEventsViewModel = viewEventsViewModel;
    }

    /**
     * Execute looking up events occuring on a given date.
     * @param date the date
     */
    public void execute(LocalDate date) {
        final List<EventSummary> matches = eventsGateway.getAllEvents()
                .stream().filter(event -> event.occursOn(date))
                .map(this::toSummary).toList();

        final ViewEventsState state = viewEventsViewModel.getState();
        state.setEvents(matches);
        state.setErrorMessage("");
        viewEventsViewModel.firePropertyChanged(ViewEventsViewModel.EVENTS_PROPERTY);
    }

    private EventSummary toSummary(EventInterface event) {
        return new EventSummary(event.getTitle(), event.getDescription(),
                event.getStartDate(), event.getEndDate());
    }
}
