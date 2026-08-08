package interface_adapter.event.view_events;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The view model for the "view events for a given date" view.
 */
public class ViewEventsViewModel {

    public static final String EVENTS_PROPERTY = "events";

    private ViewEventsState state = new ViewEventsState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ViewEventsState getState() {
        return state;
    }

    public void setState(ViewEventsState state) {
        this.state = state;
    }

    /**
     * Notify listeners when a property has changed.
     * @param propertyName the name of the property
     */
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, state);
    }

    /**
     * Register a listener for state changes.
     * @param listener the listener
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
