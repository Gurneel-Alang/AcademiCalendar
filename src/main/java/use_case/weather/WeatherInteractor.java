package use_case.weather;

import entity.weather.Weather;

import java.io.IOException;
import java.time.LocalDate;

public class WeatherInteractor implements WeatherInputBoundary{
    private final WeatherDataAccessInterface weatherDataAccessObject;
    private final WeatherOutputBoundary weatherPresenter;
    public WeatherInteractor(WeatherDataAccessInterface weatherDataAccessObject, WeatherOutputBoundary weatherPresenter) {
        this.weatherDataAccessObject = weatherDataAccessObject;
        this.weatherPresenter = weatherPresenter;
    }
    @Override
    public void execute(WeatherInputData inputData) {
        if (inputData == null) {
            weatherPresenter.prepareFailView(
                    "Weather request is required."
            );
            return;
        }

        String city = inputData.getCity();
        LocalDate date = inputData.getSelectedDate();

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

        try {
            Weather weather = weatherDataAccessObject.getWeather(
                    city.trim(),
                    date
            );

            String advice = generateAdvice(weather);
            WeatherOutputData outputData =
                    new WeatherOutputData(
                            weather.getCity(),
                            weather.getDate(),
                            weather.getTemperature(),
                            weather.getFeelsLike(),
                            weather.getCondition(),
                            weather.getDescription(),
                            weather.getHumidity(),
                            weather.getWindSpeed()
                    );

            weatherPresenter.prepareSuccessView(outputData);
        }
        catch (IOException exception) {
            weatherPresenter.prepareFailView(
                    exception.getMessage()
            );
        }
    }
    private String generateAdvice(Weather weather) {
        if ("Rain".equalsIgnoreCase(weather.getCondition())) {
            return "Bring an umbrella.";
        }
        else if (weather.getTemperature() < 5) {
            return "Wear a warm coat.";
        }
        else if (weather.getTemperature() > 28) {
            return "Stay hydrated.";
        }
        else {
            return "No special preparation is needed.";
        }
    }}