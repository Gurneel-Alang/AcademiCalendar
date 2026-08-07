package data_access.weather;

import java.time.Instant;
import java.util.Objects;

import entity.weather.Weather;

/**
 * A weather forecast together with the metadata needed by the local cache.
 */
public final class CachedWeather {

    private final String queryCity;
    private final Weather weather;
    private final Instant fetchedAt;
    private final Instant expiresAt;

    public CachedWeather(
            String queryCity,
            Weather weather,
            Instant fetchedAt,
            Instant expiresAt) {

        this.queryCity = Objects.requireNonNull(queryCity);
        this.weather = Objects.requireNonNull(weather);
        this.fetchedAt = Objects.requireNonNull(fetchedAt);
        this.expiresAt = Objects.requireNonNull(expiresAt);
    }

    public String getQueryCity() {
        return queryCity;
    }

    public Weather getWeather() {
        return weather;
    }

    public Instant getFetchedAt() {
        return fetchedAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    /**
     * Determines whether the cached forecast is still fresh.
     *
     * @param now the current time
     * @return true if the cache has not expired
     */
    public boolean isFresh(Instant now) {
        return now.isBefore(expiresAt);
    }
}
