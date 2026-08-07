package data_access.weather;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

import entity.weather.Weather;
import use_case.weather.WeatherDataAccessInterface;

/**
 * Combines the remote weather service with a persistent local cache.
 */
public final class OfflineFirstWeatherDataAccessObject
        implements WeatherDataAccessInterface {

    private static final Duration DEFAULT_CACHE_LIFETIME =
            Duration.ofHours(3);

    private final WeatherDataAccessInterface remoteDataAccessObject;
    private final WeatherCache weatherCache;
    private final Clock clock;
    private final Duration cacheLifetime;

    /**
     * Creates an offline-first weather data access object
     * with a three-hour cache lifetime.
     *
     * @param remoteDataAccessObject remote weather service
     * @param weatherCache local weather cache
     */
    public OfflineFirstWeatherDataAccessObject(
            WeatherDataAccessInterface remoteDataAccessObject,
            WeatherCache weatherCache) {

        this(
                remoteDataAccessObject,
                weatherCache,
                Clock.systemUTC(),
                DEFAULT_CACHE_LIFETIME
        );
    }

    /**
     * Constructor that allows Clock and cache lifetime injection for testing.
     *
     * @param remoteDataAccessObject remote weather service
     * @param weatherCache local weather cache
     * @param clock clock used to determine cache freshness
     * @param cacheLifetime duration for which cached weather is fresh
     */
    public OfflineFirstWeatherDataAccessObject(
            WeatherDataAccessInterface remoteDataAccessObject,
            WeatherCache weatherCache,
            Clock clock,
            Duration cacheLifetime) {

        this.remoteDataAccessObject =
                Objects.requireNonNull(remoteDataAccessObject);

        this.weatherCache =
                Objects.requireNonNull(weatherCache);

        this.clock =
                Objects.requireNonNull(clock);

        this.cacheLifetime =
                Objects.requireNonNull(cacheLifetime);

        if (cacheLifetime.isZero()
                || cacheLifetime.isNegative()) {

            throw new IllegalArgumentException(
                    "Cache lifetime must be positive."
            );
        }
    }

    @Override
    public Weather getWeather(
            String city,
            LocalDate date) throws IOException {

        final Instant now = clock.instant();

        Optional<CachedWeather> cachedWeather =
                Optional.empty();

        IOException cacheReadFailure = null;

        try {
            cachedWeather =
                    weatherCache.find(city, date);
        }
        catch (IOException exception) {
            cacheReadFailure = exception;
        }

        /*
         * Fresh local data can be returned immediately,
         * without making a network request.
         */
        if (cachedWeather.isPresent()
                && cachedWeather.get().isFresh(now)) {

            return cachedWeather.get().getWeather();
        }

        try {
            /*
             * No fresh cache is available, so request
             * the latest weather from OpenWeather.
             */
            final Weather remoteWeather =
                    remoteDataAccessObject.getWeather(
                            city,
                            date
                    );

            final CachedWeather newCachedWeather =
                    new CachedWeather(
                            city,
                            remoteWeather,
                            now,
                            now.plus(cacheLifetime)
                    );

            try {
                weatherCache.save(newCachedWeather);
            }
            catch (IOException exception) {
                /*
                 * Valid network data should still be returned
                 * if saving the cache fails.
                 */
            }

            return remoteWeather;
        }
        catch (IOException remoteFailure) {
            /*
             * If the network fails, expired cached data
             * is still better than displaying no weather.
             */
            if (cachedWeather.isPresent()) {
                return cachedWeather
                        .get()
                        .getWeather();
            }

            if (cacheReadFailure != null) {
                remoteFailure.addSuppressed(
                        cacheReadFailure
                );
            }

            throw remoteFailure;
        }
    }
}