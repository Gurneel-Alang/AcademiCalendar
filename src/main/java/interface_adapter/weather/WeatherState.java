package interface_adapter.weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the current weather-view state.
 */
public class WeatherState {

    private String city = "";
    private LocalDate date;
    private String temperature = "";
    private String feelsLike = "";
    private String condition = "";
    private String description = "";
    private String humidity = "";
    private String windSpeed = "";
    private String error = "";
    private String advice = "";
    private List<TemperaturePoint> temperaturePoints =
            new ArrayList<>();

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<TemperaturePoint> getTemperaturePoints() {
        return new ArrayList<>(temperaturePoints);
    }

    public void setTemperaturePoints(
            List<TemperaturePoint> temperaturePoints) {

        this.temperaturePoints =
                new ArrayList<>(temperaturePoints);
    }
}
