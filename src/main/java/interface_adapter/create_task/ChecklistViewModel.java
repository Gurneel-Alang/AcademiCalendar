package interface_adapter.create_task;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Observable ViewModel for the checklist.
 */
public class ChecklistViewModel {

    public static final String STATE_PROPERTY = "checklistState";

    private ChecklistState state = new ChecklistState();

    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    public ChecklistState getState() {
        return state;
    }

    public void setState(ChecklistState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange(
                STATE_PROPERTY,
                null,
                state
        );
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener
    ) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(
            PropertyChangeListener listener
    ) {
        support.removePropertyChangeListener(listener);
    }
}