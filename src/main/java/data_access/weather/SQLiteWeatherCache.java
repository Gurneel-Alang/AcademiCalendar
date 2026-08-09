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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import entity.weather.ForecastSlot;
import entity.weather.Weather;

/**
 * Stores weather forecasts in a local SQLite database.
 */
public final class SQLiteWeatherCache implements WeatherCache {

    private static final String CREATE_WEATHER_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS weather_cache ("
                    + "query_city TEXT NOT NULL, "
                    + "forecast_date TEXT NOT NULL, "
                    + "display_city TEXT NOT NULL, "
                    + "fetched_at TEXT NOT NULL, "
                    + "expires_at TEXT NOT NULL, "
                    + "PRIMARY KEY (query_city, forecast_date)"
                    + ")";

    private static final String CREATE_SLOT_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS weather_forecast_slots ("
                    + "query_city TEXT NOT NULL, "
                    + "forecast_date TEXT NOT NULL, "
                    + "forecast_time TEXT NOT NULL, "
                    + "temperature REAL NOT NULL, "
                    + "feels_like REAL NOT NULL, "
                    + "condition TEXT NOT NULL, "
                    + "description TEXT NOT NULL, "
                    + "humidity INTEGER NOT NULL, "
                    + "wind_speed REAL NOT NULL, "
                    + "precipitation_probability REAL NOT NULL, "
                    + "PRIMARY KEY ("
                    + "query_city, forecast_date, forecast_time"
                    + ")"
                    + ")";

    private static final String FIND_WEATHER_SQL =
            "SELECT display_city, fetched_at, expires_at "
                    + "FROM weather_cache "
                    + "WHERE query_city = ? "
                    + "AND forecast_date = ?";

    private static final String FIND_SLOTS_SQL =
            "SELECT forecast_time, temperature, feels_like, "
                    + "condition, description, humidity, "
                    + "wind_speed, precipitation_probability "
                    + "FROM weather_forecast_slots "
                    + "WHERE query_city = ? "
                    + "AND forecast_date = ? "
                    + "ORDER BY forecast_time";

    private static final String SAVE_WEATHER_SQL =
            "INSERT INTO weather_cache ("
                    + "query_city, forecast_date, display_city, "
                    + "fetched_at, expires_at"
                    + ") VALUES (?, ?, ?, ?, ?) "
                    + "ON CONFLICT(query_city, forecast_date) "
                    + "DO UPDATE SET "
                    + "display_city = excluded.display_city, "
                    + "fetched_at = excluded.fetched_at, "
                    + "expires_at = excluded.expires_at";

    private static final String DELETE_SLOTS_SQL =
            "DELETE FROM weather_forecast_slots "
                    + "WHERE query_city = ? "
                    + "AND forecast_date = ?";

    private static final String SAVE_SLOT_SQL =
            "INSERT INTO weather_forecast_slots ("
                    + "query_city, forecast_date, forecast_time, "
                    + "temperature, feels_like, condition, "
                    + "description, humidity, wind_speed, "
                    + "precipitation_probability"
                    + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String connectionUrl;

    /**
     * Creates a SQLite weather cache.
     *
     * @param databasePath path to the SQLite database file
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

        final String normalizedCity =
                normalizeCity(city);

        try (Connection connection = openConnection();
             PreparedStatement weatherStatement =
                     connection.prepareStatement(
                             FIND_WEATHER_SQL
                     )) {

            weatherStatement.setString(
                    1,
                    normalizedCity
            );

            weatherStatement.setString(
                    2,
                    date.toString()
            );

            try (ResultSet weatherResult =
                         weatherStatement.executeQuery()) {

                if (!weatherResult.next()) {
                    return Optional.empty();
                }

                final String displayCity =
                        weatherResult.getString(
                                "display_city"
                        );

                final Instant fetchedAt =
                        Instant.parse(
                                weatherResult.getString(
                                        "fetched_at"
                                )
                        );

                final Instant expiresAt =
                        Instant.parse(
                                weatherResult.getString(
                                        "expires_at"
                                )
                        );

                final List<ForecastSlot> forecastSlots =
                        findForecastSlots(
                                connection,
                                normalizedCity,
                                date
                        );

                if (forecastSlots.isEmpty()) {
                    return Optional.empty();
                }

                final Weather weather =
                        new Weather(
                                displayCity,
                                date,
                                forecastSlots
                        );

                return Optional.of(
                        new CachedWeather(
                                city,
                                weather,
                                fetchedAt,
                                expiresAt
                        )
                );
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
    public void save(
            CachedWeather cachedWeather)
            throws IOException {

        final Weather weather =
                cachedWeather.getWeather();

        final String normalizedCity =
                normalizeCity(
                        cachedWeather.getQueryCity()
                );

        try (Connection connection =
                     openConnection()) {

            connection.setAutoCommit(false);

            try {
                saveWeatherMetadata(
                        connection,
                        normalizedCity,
                        cachedWeather
                );

                try (PreparedStatement deleteStatement =
                             connection.prepareStatement(
                                     DELETE_SLOTS_SQL
                             )) {

                    deleteStatement.setString(
                            1,
                            normalizedCity
                    );

                    deleteStatement.setString(
                            2,
                            weather
                                    .getDate()
                                    .toString()
                    );

                    deleteStatement.executeUpdate();
                }

                saveForecastSlots(
                        connection,
                        normalizedCity,
                        weather
                );

                connection.commit();
            }
            catch (SQLException exception) {
                try {
                    connection.rollback();
                }
                catch (SQLException rollbackException) {
                    exception.addSuppressed(
                            rollbackException
                    );
                }

                throw new IOException(
                        "Could not save the weather cache.",
                        exception
                );
            }
        }
        catch (SQLException exception) {
            throw new IOException(
                    "Could not access the weather cache.",
                    exception
            );
        }
    }

    private List<ForecastSlot> findForecastSlots(
            Connection connection,
            String normalizedCity,
            LocalDate date)
            throws SQLException {

        final List<ForecastSlot> forecastSlots =
                new ArrayList<>();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             FIND_SLOTS_SQL
                     )) {

            statement.setString(
                    1,
                    normalizedCity
            );

            statement.setString(
                    2,
                    date.toString()
            );

            try (ResultSet result =
                         statement.executeQuery()) {

                while (result.next()) {
                    forecastSlots.add(
                            new ForecastSlot(
                                    LocalDateTime.parse(
                                            result.getString(
                                                    "forecast_time"
                                            )
                                    ),
                                    result.getDouble(
                                            "temperature"
                                    ),
                                    result.getDouble(
                                            "feels_like"
                                    ),
                                    result.getString(
                                            "condition"
                                    ),
                                    result.getString(
                                            "description"
                                    ),
                                    result.getInt(
                                            "humidity"
                                    ),
                                    result.getDouble(
                                            "wind_speed"
                                    ),
                                    result.getDouble(
                                            "precipitation_probability"
                                    )
                            )
                    );
                }
            }
        }

        return forecastSlots;
    }

    private void saveWeatherMetadata(
            Connection connection,
            String normalizedCity,
            CachedWeather cachedWeather)
            throws SQLException {

        final Weather weather =
                cachedWeather.getWeather();

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             SAVE_WEATHER_SQL
                     )) {

            statement.setString(
                    1,
                    normalizedCity
            );

            statement.setString(
                    2,
                    weather.getDate().toString()
            );

            statement.setString(
                    3,
                    weather.getCity()
            );

            statement.setString(
                    4,
                    cachedWeather
                            .getFetchedAt()
                            .toString()
            );

            statement.setString(
                    5,
                    cachedWeather
                            .getExpiresAt()
                            .toString()
            );

            statement.executeUpdate();
        }
    }

    private void saveForecastSlots(
            Connection connection,
            String normalizedCity,
            Weather weather)
            throws SQLException {

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             SAVE_SLOT_SQL
                     )) {

            for (ForecastSlot forecastSlot
                    : weather.getForecastSlots()) {

                statement.setString(
                        1,
                        normalizedCity
                );

                statement.setString(
                        2,
                        weather
                                .getDate()
                                .toString()
                );

                statement.setString(
                        3,
                        forecastSlot
                                .getDateTime()
                                .toString()
                );

                statement.setDouble(
                        4,
                        forecastSlot
                                .getTemperature()
                );

                statement.setDouble(
                        5,
                        forecastSlot
                                .getFeelsLike()
                );

                statement.setString(
                        6,
                        forecastSlot
                                .getCondition()
                );

                statement.setString(
                        7,
                        forecastSlot
                                .getDescription()
                );

                statement.setInt(
                        8,
                        forecastSlot
                                .getHumidity()
                );

                statement.setDouble(
                        9,
                        forecastSlot
                                .getWindSpeed()
                );

                statement.setDouble(
                        10,
                        forecastSlot
                                .getPrecipitationProbability()
                );

                statement.addBatch();
            }

            statement.executeBatch();
        }
    }

    private void initializeDatabase()
            throws IOException {

        try (Connection connection =
                     openConnection();
             Statement statement =
                     connection.createStatement()) {

            statement.executeUpdate(
                    CREATE_WEATHER_TABLE_SQL
            );

            statement.executeUpdate(
                    CREATE_SLOT_TABLE_SQL
            );
        }
        catch (SQLException exception) {
            throw new IOException(
                    "Could not initialize the weather cache.",
                    exception
            );
        }
    }

    private Connection openConnection()
            throws SQLException {

        return DriverManager.getConnection(
                connectionUrl
        );
    }

    private String normalizeCity(
            String city) {

        return city
                .trim()
                .toLowerCase(Locale.ROOT);
    }
}