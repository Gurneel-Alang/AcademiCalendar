package use_case.weather;
import entity.weather.Weather;
import java.io.IOException;
import java.time.LocalDate;

public interface WeatherDataAccessInterface {

    /**

     * Retrieves the weather forecast for the specified city and date.
     *
     * @param city the city name
     * @param date the selected date
     * @return the weather information
     * @throws IOException if the weather service cannot be reached
     */
    Weather getWeather(String city, LocalDate date) throws IOException;

}