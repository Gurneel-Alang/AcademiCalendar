package data_access.weather;

import java.io.IOException;

/**
 * Provides access to an external weather service.
 */
public interface WeatherApiClient {

    /**
     * Fetches location information for a city.
     *
     * @param city city entered by the user
     * @return raw JSON response
     * @throws IOException if the request fails
     */
    String fetchLocation(String city) throws IOException;

    /**
     * Fetches forecast information for a location.
     *
     * @param latitude latitude of the location
     * @param longitude longitude of the location
     * @return raw JSON response
     * @throws IOException if the request fails
     */
    String fetchForecast(double latitude, double longitude)
            throws IOException;
}
