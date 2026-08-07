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

    public Weather(String city,
                   LocalDate date,
                   double temperature,
                   double feelsLike,
                   String condition,
                   String description,
                   int humidity,
                   double windSpeed) {
        this.city = city;
        this.date = date;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.condition = condition;
        this.description = description;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
    }

    public String getCity() {
        return city;

    }

    public LocalDate getDate() {

        return date;

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

}