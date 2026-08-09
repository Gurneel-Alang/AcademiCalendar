package interface_adapter.reminder.schedule_reminder;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View model for the Schedule Reminder use case.
 */
public class ScheduleReminderViewModel {

    public static final String CONFIRM_PROPERTY = "confirm";
    public static final String FIRED_PROPERTY = "fired";

    private ScheduleReminderState state = new ScheduleReminderState();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public ScheduleReminderState getState() {
        return state;
    }

    /**
     * Notify listeners who are registered in the command below if anything changes.
     * @param propertyName name of the item that changed
     */
    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, state);
    }

    /**
     * Add a listener to the registered list to get notified if things changed.
     * @param listener the listener or view to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}