package interface_adapter.checklist;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * all checklist presenters can update this one view model
 */
public class ChecklistViewModel {
    private ChecklistState state = new ChecklistState();
    private final PropertyChangeSupport  support = new PropertyChangeSupport(this);

    public ChecklistState getState() {
        return state;
    }

    public void setState(ChecklistState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener
    ){
        support.addPropertyChangeListener(listener);
    }
}
