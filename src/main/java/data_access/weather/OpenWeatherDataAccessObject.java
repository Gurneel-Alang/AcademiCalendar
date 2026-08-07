package data_access.weather;

import java.io.IOException;
import java.time.LocalDate;

import data_access.weather.dto.ForecastResponse;
import data_access.weather.dto.LocationResponse;
import entity.weather.Weather;
import use_case.weather.WeatherDataAccessInterface;

/**
 * Coordinates the retrieval of weather forecast information.
 */
public class OpenWeatherDataAccessObject
        implements WeatherDataAccessInterface {

    private final WeatherApiClient apiClient;
    private final OpenWeatherJsonParser jsonParser;
    private final ForecastSelector forecastSelector;

    public OpenWeatherDataAccessObject(
            WeatherApiClient apiClient,
            OpenWeatherJsonParser jsonParser,
            ForecastSelector forecastSelector) {

        this.apiClient = apiClient;
        this.jsonParser = jsonParser;
        this.forecastSelector = forecastSelector;
    }

    @Override
    public Weather getWeather(
            String city,
            LocalDate date) throws IOException {

        if (city == null || city.isBlank()) {
            throw new IOException(
                    "A city must be provided."
            );
        }

        final String locationJson =
                apiClient.fetchLocation(city.trim());

        final LocationResponse location =
                jsonParser.parseLocation(locationJson);

        final String forecastJson =
                apiClient.fetchForecast(
                        location.getLat(),
                        location.getLon()
                );

        final ForecastResponse forecastResponse =
                jsonParser.parseForecast(forecastJson);

        return forecastSelector.selectWeather(
                forecastResponse,
                location,
                date
        );
    }
}
