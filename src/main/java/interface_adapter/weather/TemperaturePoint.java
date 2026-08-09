package interface_adapter.weather;

import java.time.LocalTime;
import java.util.Objects;

/**
 * Represents one point shown on the temperature chart.
 */
public final class TemperaturePoint {

    private final LocalTime time;
    private final double temperature;

    /**
     * Creates a temperature point.
     * @param time local forecast time
     * @param temperature temperature in degrees Celsius
     */
    public TemperaturePoint(
            LocalTime time,
            double temperature) {

        this.time = Objects.requireNonNull(time);
        this.temperature = temperature;
    }

    public LocalTime getTime() {
        return time;
    }

    public double getTemperature() {
        return temperature;
    }
}