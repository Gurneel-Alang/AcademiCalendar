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

    public void firePropertyChanged(String propertyName) {
        support.firePropertyChange(propertyName, null, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}