package interface_adapter.weather;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Holds weather state and notifies the Swing view when it changes.
 */
public class WeatherViewModel {

    public static final String PROPERTY_NAME = "weather";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private WeatherState state = new WeatherState();

    public WeatherState getState() {
        return state;
    }

    public void setState(WeatherState state) {
        this.state = state;
    }

    public void firePropertyChanged() {
        support.firePropertyChange(PROPERTY_NAME, null, state);
    }

    public void addPropertyChangeListener(
            PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
