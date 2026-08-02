package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The model for the view manager.
 */
public class ViewManagerModel {

    public static final String ACTIVE_VIEW_PROPERTY = "activeView";

    private String activeView;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getActiveView() { return activeView; }

    public void setActiveView(String activeView) { this.activeView = activeView; }

    /**
     * Notify listeners that the active view should change.
     */
    public void firePropertyChanged() {
        support.firePropertyChange(ACTIVE_VIEW_PROPERTY, null, activeView);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
            support.addPropertyChangeListener(listener);
    }
}
