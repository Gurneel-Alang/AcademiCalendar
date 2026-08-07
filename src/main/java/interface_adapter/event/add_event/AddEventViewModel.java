package interface_adapter.event.add_event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The view model for the Add Event view.
 */
public class AddEventViewModel {

    public static final String CLOSE_PROPERTY = "close";
    public static final String ERROR_PROPERTY = "error";

    private AddEventState state = new AddEventState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public AddEventState getState() { return state; }

    public void setState(AddEventState state) { this.state = state; }

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
