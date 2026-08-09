package use_case.weather;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Output data represent one forecast time slot.
 * transfers forecast information from usecase layer to the presenter without exposing the ForecastSlot entity directly.
 */
public final class ForecastSlotOutputData {
    private final LocalDateTime dateTime;
    private final double temperature;
    private final double feelsLike;
    private final String condition;
    private final String description;
    private final int humidity;
    private final double windSpeed;
    private final double precipitationProbability;
    /**
     * Creates forecast slot output data.
     * @param dateTime local date and time of the forecast
     * @param temperature temperature in degrees Celsius
     * @param feelsLike feels-like temperature in degrees Celsius
     * @param condition general weather condition
     * @param description detailed weather description
     * @param humidity humidity percentage
     * @param windSpeed wind speed
     * @param precipitationProbability probability of precipitation
     */
    public ForecastSlotOutputData(
            LocalDateTime dateTime,
            double temperature,
            double feelsLike,
            String condition,
            String description,
            int humidity,
            double windSpeed,
            double precipitationProbability) {

        this.dateTime = Objects.requireNonNull(dateTime);
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.condition = Objects.requireNonNull(condition);
        this.description = Objects.requireNonNull(description);
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.precipitationProbability = precipitationProbability;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getFeelsLike() {
        return feelsLike;
    }

    public String getCondition() {
        return condition;
    }

    public String getDescription() {
        return description;
    }

    public int getHumidity() {
        return humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getPrecipitationProbability() {
        return precipitationProbability;
    }
}