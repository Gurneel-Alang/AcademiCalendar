package use_case.weather;

import java.io.IOException;
import java.time.LocalDate;

import entity.weather.Weather;

public interface WeatherDataAccessInterface {

    /**
     * Retrieves the weather forecast for the specified city and date.
     * @param city the city name
     * @param date the selected date
     * @return the weather information
     * @throws IOException if the weather service cannot be reached
     */
    Weather getWeather(String city, LocalDate date) throws IOException;
}
