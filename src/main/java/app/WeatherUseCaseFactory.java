package app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import data_access.weather.ForecastSelector;
import data_access.weather.OfflineFirstWeatherDataAccessObject;
import data_access.weather.OpenWeatherApiClient;
import data_access.weather.OpenWeatherDataAccessObject;
import data_access.weather.OpenWeatherJsonParser;
import data_access.weather.SQLiteWeatherCache;
import data_access.weather.WeatherApiClient;
import data_access.weather.WeatherCache;
import interface_adapter.weather.WeatherController;
import interface_adapter.weather.WeatherPresenter;
import interface_adapter.weather.WeatherViewModel;
import use_case.weather.WeatherDataAccessInterface;
import use_case.weather.WeatherInputBoundary;
import use_case.weather.WeatherInteractor;
import use_case.weather.WeatherOutputBoundary;
import view.WeatherView;

/**
 * Creates and connects the components of the weather use case.
 */
public final class WeatherUseCaseFactory {

    private WeatherUseCaseFactory() {
    }

    /**
     * Creates the weather view using the default local database.
     * @param apiKey OpenWeather API key
     * @return fully configured weather view
     */
    public static WeatherView create(String apiKey) {
        final Path databasePath =
                Paths.get(
                        System.getProperty("user.home"),
                        ".academicalendar",
                        "academicalendar.db"
                );

        return create(apiKey, databasePath);
    }

    /**
     * Creates the weather view using a specified database path.
     *
     * The database-path parameter makes integration testing easier,
     * because tests can use a temporary database.
     *
     * @param apiKey OpenWeather API key
     * @param databasePath location of the SQLite database
     * @return fully configured weather view
     */
    public static WeatherView create(
            String apiKey,
            Path databasePath) {

        final WeatherApiClient apiClient =
                new OpenWeatherApiClient(apiKey);

        final OpenWeatherJsonParser parser =
                new OpenWeatherJsonParser();

        final ForecastSelector selector =
                new ForecastSelector();

        /*
         * Remote data source.
         */
        final WeatherDataAccessInterface remoteDao =
                new OpenWeatherDataAccessObject(
                        apiClient,
                        parser,
                        selector
                );

        /*
         * Local data source.
         */
        final WeatherCache weatherCache;

        try {
            weatherCache =
                    new SQLiteWeatherCache(
                            databasePath
                    );
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not initialize the weather database.",
                    exception
            );
        }

        /*
         * Combines the remote and local data sources.
         */
        final WeatherDataAccessInterface weatherDao =
                new OfflineFirstWeatherDataAccessObject(
                        remoteDao,
                        weatherCache
                );

        final WeatherViewModel viewModel =
                new WeatherViewModel();

        final WeatherOutputBoundary presenter =
                new WeatherPresenter(viewModel);

        final WeatherInputBoundary interactor =
                new WeatherInteractor(
                        weatherDao,
                        presenter
                );

        final WeatherController controller =
                new WeatherController(interactor);

        return new WeatherView(
                viewModel,
                controller
        );
    }
}