package interface_adapter.checklist;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * All checklist presenters can update this one view model.
 */
public class ChecklistViewModel {
    private ChecklistState state = new ChecklistState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * Return the state.
     * @return the state
     */
    public ChecklistState getState() {
        return state;
    }

    /**
     * Set the state.
     * @param state the state to set
     */
    public void setState(ChecklistState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
