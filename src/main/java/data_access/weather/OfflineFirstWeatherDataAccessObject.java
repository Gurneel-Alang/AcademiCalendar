package data_access.weather;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;

import data_access.weather.cache.CachedWeather;
import data_access.weather.cache.WeatherCache;
import entity.weather.Weather;
import use_case.weather.WeatherDataAccessInterface;

/**
 * Combines the remote weather service with a persistent local cache.
 */
public final class OfflineFirstWeatherDataAccessObject implements WeatherDataAccessInterface {
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
     * @throws IllegalArgumentException if cache liftime is negative
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

        final Instant now =
                clock.instant();

        CachedWeather cachedWeather = null;
        IOException cacheFailure = null;

        try {
            cachedWeather =
                    weatherCache
                            .find(city, date)
                            .orElse(null);
        } catch (IOException exception) {
            cacheFailure = exception;
        }

        if (cachedWeather != null
                && cachedWeather.isFresh(now)) {

            return cachedWeather.getWeather();
        }

        try {
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
                weatherCache.save(
                        newCachedWeather
                );
            } catch (IOException ignored) {
                // Valid remote data can still be shown.
            }

            return remoteWeather;
        } catch (IOException remoteFailure) {

            if (cachedWeather != null) {
                return cachedWeather.getWeather();
            }

            if (cacheFailure != null) {
                remoteFailure.addSuppressed(
                        cacheFailure
                );
            }

            throw remoteFailure;
        }
    }}
