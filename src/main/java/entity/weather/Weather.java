package entity.weather;

import java.time.LocalDate;

public class Weather {
    private final String city;
    private final LocalDate date;
    private final double temperature;
    private final double feelsLike;
    private final String condition;
    private final String description;
    private final int humidity;
    private final double windSpeed;

    public Weather(String city, LocalDate date, double temperature, double feelsLike,
                   String condition, String description, int humidity, double windSpeed) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    /**
     * Return the city.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Return the date.
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Return the temperature.
     * @return the temperature
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Return the "feels like" temperature.
     * @return the "feels like" temperature
     */
    public double getFeelsLike() {
        return feelsLike;
    }

    /**
     * Return the condition.
     * @return the condition
     */
    public String getCondition() {
        return condition;
    }

    /**
     * Return the description.
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the humidity.
     * @return the humidity
     */
    public int getHumidity() {
        return humidity;
    }

    /**
     * Return the wind speed.
     * @return the wind speed
     */
    public double getWindSpeed() {
        return windSpeed;
    }
}
