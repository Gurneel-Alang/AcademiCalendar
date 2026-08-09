package app;

import java.io.IOException;
import java.nio.file.Path;
import java.time.Clock;
import java.time.Duration;
import java.util.List;

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

import use_case.weather.advice.HeatAdviceStrategy;
import use_case.weather.advice.RainAdviceStrategy;
import use_case.weather.advice.WeatherAdviceGenerator;
import use_case.weather.WeatherDataAccessInterface;
import use_case.weather.WeatherInputBoundary;
import use_case.weather.WeatherInteractor;
import use_case.weather.WeatherOutputBoundary;

import use_case.weather.advice.WindAdviceStrategy;
import view.WeatherView;

/**
 * Creates and wires the weather use case.
 */
public final class WeatherUseCaseFactory {

    private static final Duration CACHE_LIFETIME =
            Duration.ofHours(3);

    private WeatherUseCaseFactory() {
    }

    /**
     * Creates the weather feature.
     *
     * @param apiKey OpenWeather API key
     * @return weather view
     * @throws IllegalStateException if the local weather cache
     *                               cannot be initialized
     */
    public static WeatherView create(String apiKey) {
        final WeatherApiClient apiClient =
                new OpenWeatherApiClient(apiKey);

        final OpenWeatherJsonParser parser =
                new OpenWeatherJsonParser();

        final ForecastSelector selector =
                new ForecastSelector();

        final WeatherDataAccessInterface remoteDataAccess =
                new OpenWeatherDataAccessObject(
                        apiClient,
                        parser,
                        selector
                );

        final WeatherDataAccessInterface weatherDataAccess;

        try {
            final Path databasePath =
                    Path.of(
                            System.getProperty("user.home"),
                            ".academicalendar",
                            "weather.db"
                    );

            final WeatherCache cache =
                    new SQLiteWeatherCache(databasePath);

            weatherDataAccess =
                    new OfflineFirstWeatherDataAccessObject(
                            remoteDataAccess,
                            cache,
                            Clock.systemUTC(),
                            CACHE_LIFETIME
                    );
        }
        catch (IOException exception) {
            throw new IllegalStateException(
                    "Could not initialize the weather cache.",
                    exception
            );
        }

        final WeatherViewModel viewModel =
                new WeatherViewModel();

        final WeatherOutputBoundary presenter =
                new WeatherPresenter(viewModel);

        final WeatherAdviceGenerator adviceGenerator =
                new WeatherAdviceGenerator(List.of(
                        new RainAdviceStrategy(),
                        new HeatAdviceStrategy(),
                        new WindAdviceStrategy()
                ));

        final WeatherInputBoundary interactor =
                new WeatherInteractor(
                        weatherDataAccess,
                        presenter,
                        adviceGenerator
                );

        final WeatherController controller =
                new WeatherController(interactor);

        return new WeatherView(
                viewModel,
                controller
        );
    }
}
