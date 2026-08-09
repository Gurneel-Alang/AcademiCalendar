package use_case.weather;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entity.weather.ForecastSlot;
import entity.weather.Weather;
import use_case.weather.advice.WeatherAdviceGenerator;

/**
 * Handles the weather use case.
 */
public class WeatherInteractor implements WeatherInputBoundary {

    private final WeatherDataAccessInterface weatherDataAccessObject;
    private final WeatherOutputBoundary weatherPresenter;
    private final WeatherAdviceGenerator weatherAdviceGenerator;

    /**
     * Creates a weather interactor.
     *
     * @param weatherDataAccessObject weather data access
     * @param weatherPresenter weather output boundary
     * @param weatherAdviceGenerator generates advice from forecast data
     */
    public WeatherInteractor(
            WeatherDataAccessInterface weatherDataAccessObject,
            WeatherOutputBoundary weatherPresenter,
            WeatherAdviceGenerator weatherAdviceGenerator) {

        this.weatherDataAccessObject = weatherDataAccessObject;
        this.weatherPresenter = weatherPresenter;
        this.weatherAdviceGenerator = weatherAdviceGenerator;
    }

    @Override
    public void execute(WeatherInputData inputData) {
        if (inputData == null) {
            weatherPresenter.prepareFailView(
                    "Weather request is required."
            );
            return;
        }

        final String city = inputData.getCity();
        final LocalDate date = inputData.getSelectedDate();

        if (city == null || city.trim().isEmpty()) {
            weatherPresenter.prepareFailView(
                    "Please enter a city."
            );
            return;
        }

        if (date == null) {
            weatherPresenter.prepareFailView(
                    "Please select a date."
            );
            return;
        }

        try {final Weather weather =
                    weatherDataAccessObject.getWeather(
                            city.trim(),
                            date
                    );

            final ForecastSlot representativeSlot =
                    weather.getRepresentativeSlot();

            final String advice =
                    weatherAdviceGenerator.generate(
                            weather.getForecastSlots()
                    );

            final List<ForecastSlotOutputData> forecastSlots =
                    convertForecastSlots(
                            weather.getForecastSlots()
                    );

            final WeatherOutputData outputData =
                    new WeatherOutputData(
                            weather.getCity(),
                            weather.getDate(),
                            convertForecastSlot(
                                    representativeSlot
                            ),
                            advice,
                            forecastSlots
                    );

            weatherPresenter.prepareSuccessView(outputData);
        }
        catch (IOException exception) {
            weatherPresenter.prepareFailView(
                    exception.getMessage()
            );
        }
    }

    /**
     * Converts forecast entities into output data.
     *
     * @param forecastSlots forecast entities
     * @return forecast output data
     */
    private List<ForecastSlotOutputData> convertForecastSlots(
            List<ForecastSlot> forecastSlots) {

        final List<ForecastSlotOutputData> outputSlots =
                new ArrayList<>();

        for (ForecastSlot forecastSlot : forecastSlots) {
            outputSlots.add(
                    convertForecastSlot(forecastSlot)
            );
        }

        return outputSlots;
    }

    /**
     * Converts one forecast entity into output data.
     *
     * @param forecastSlot forecast entity
     * @return forecast output data
     */
    private ForecastSlotOutputData convertForecastSlot(
            ForecastSlot forecastSlot) {

        return new ForecastSlotOutputData(
                forecastSlot.getDateTime(),
                forecastSlot.getTemperature(),
                forecastSlot.getFeelsLike(),
                forecastSlot.getCondition(),
                forecastSlot.getDescription(),
                forecastSlot.getHumidity(),
                forecastSlot.getWindSpeed(),
                forecastSlot.getPrecipitationProbability()
        );
    }
}