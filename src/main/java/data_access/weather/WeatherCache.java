package data_access.weather;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Provides access to locally cached weather forecasts.
 */
public interface WeatherCache {
    /**
     * Finds cached weather for a city and date.
     *
     * @param city the city originally entered by the user
     * @param date the forecast date
     * @return cached weather, or an empty Optional if none exists
     * @throws IOException if the cache cannot be read
     */
    Optional<CachedWeather> find(String city, LocalDate date)
            throws IOException;

    /**
     * Saves weather and its cache metadata.
     *
     * @param cachedWeather weather to save
     * @throws IOException if the cache cannot be written
     */
    void save(CachedWeather cachedWeather) throws IOException;
}
