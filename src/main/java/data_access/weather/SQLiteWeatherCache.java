package data_access.weather;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Optional;

import entity.weather.Weather;

/**
 * Stores weather forecasts in a local SQLite database.
 */
public final class SQLiteWeatherCache implements WeatherCache {

    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS weather_cache ("
                    + "query_city TEXT NOT NULL, "
                    + "forecast_date TEXT NOT NULL, "
                    + "display_city TEXT NOT NULL, "
                    + "temperature REAL NOT NULL, "
                    + "feels_like REAL NOT NULL, "
                    + "condition TEXT NOT NULL, "
                    + "description TEXT NOT NULL, "
                    + "humidity INTEGER NOT NULL, "
                    + "wind_speed REAL NOT NULL, "
                    + "fetched_at TEXT NOT NULL, "
                    + "expires_at TEXT NOT NULL, "
                    + "PRIMARY KEY (query_city, forecast_date)"
                    + ")";

    private static final String FIND_SQL =
            "SELECT display_city, temperature, feels_like, condition, "
                    + "description, humidity, wind_speed, fetched_at, "
                    + "expires_at FROM weather_cache "
                    + "WHERE query_city = ? AND forecast_date = ?";

    private static final String SAVE_SQL =
            "INSERT INTO weather_cache (query_city, forecast_date, "
                    + "display_city, temperature, feels_like, condition, "
                    + "description, humidity, wind_speed, fetched_at, "
                    + "expires_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "
                    + "ON CONFLICT(query_city, forecast_date) DO UPDATE SET "
                    + "display_city = excluded.display_city, "
                    + "temperature = excluded.temperature, "
                    + "feels_like = excluded.feels_like, "
                    + "condition = excluded.condition, "
                    + "description = excluded.description, "
                    + "humidity = excluded.humidity, "
                    + "wind_speed = excluded.wind_speed, "
                    + "fetched_at = excluded.fetched_at, "
                    + "expires_at = excluded.expires_at";

    private final String connectionUrl;

    /**
     * Creates a SQLite weather cache.
     *
     * @param databasePath path of the SQLite database file
     * @throws IOException if the database cannot be initialized
     */
    public SQLiteWeatherCache(Path databasePath) throws IOException {
        final Path absolutePath = databasePath.toAbsolutePath();
        final Path parent = absolutePath.getParent();

        if (parent != null) {
            Files.createDirectories(parent);
        }

        connectionUrl = "jdbc:sqlite:" + absolutePath;
        initializeDatabase();
    }

    @Override
    public Optional<CachedWeather> find(
            String city,
            LocalDate date) throws IOException {

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(FIND_SQL)) {

            statement.setString(1, normalizeCity(city));
            statement.setString(2, date.toString());

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    return Optional.empty();
                }

                final Weather weather = new Weather(
                        result.getString("display_city"),
                        date,
                        result.getDouble("temperature"),
                        result.getDouble("feels_like"),
                        result.getString("condition"),
                        result.getString("description"),
                        result.getInt("humidity"),
                        result.getDouble("wind_speed")
                );

                final CachedWeather cachedWeather =
                        new CachedWeather(
                                city,
                                weather,
                                Instant.parse(
                                        result.getString("fetched_at")
                                ),
                                Instant.parse(
                                        result.getString("expires_at")
                                )
                        );

                return Optional.of(cachedWeather);
            }
        }
        catch (SQLException | RuntimeException exception) {
            throw new IOException(
                    "Could not read the weather cache.",
                    exception
            );
        }
    }

    @Override
    public void save(CachedWeather cachedWeather)
            throws IOException {

        final Weather weather = cachedWeather.getWeather();

        try (Connection connection = openConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SAVE_SQL)) {

            statement.setString(
                    1,
                    normalizeCity(cachedWeather.getQueryCity())
            );
            statement.setString(
                    2,
                    weather.getDate().toString()
            );
            statement.setString(
                    3,
                    weather.getCity()
            );
            statement.setDouble(
                    4,
                    weather.getTemperature()
            );
            statement.setDouble(
                    5,
                    weather.getFeelsLike()
            );
            statement.setString(
                    6,
                    weather.getCondition()
            );
            statement.setString(
                    7,
                    weather.getDescription()
            );
            statement.setInt(
                    8,
                    weather.getHumidity()
            );
            statement.setDouble(
                    9,
                    weather.getWindSpeed()
            );
            statement.setString(
                    10,
                    cachedWeather.getFetchedAt().toString()
            );
            statement.setString(
                    11,
                    cachedWeather.getExpiresAt().toString()
            );

            statement.executeUpdate();
        }
        catch (SQLException exception) {
            throw new IOException(
                    "Could not save the weather cache.",
                    exception
            );
        }
    }

    private void initializeDatabase() throws IOException {
        try (Connection connection = openConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(CREATE_TABLE_SQL);
        }
        catch (SQLException exception) {
            throw new IOException(
                    "Could not initialize the weather cache.",
                    exception
            );
        }
    }

    private Connection openConnection() throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }

    private String normalizeCity(String city) {
        return city.trim().toLowerCase(Locale.ROOT);
    }
}