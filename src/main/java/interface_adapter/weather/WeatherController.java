package interface_adapter.weather;

import java.time.LocalDate;

import use_case.weather.WeatherInputBoundary;
import use_case.weather.WeatherInputData;

/**
 * Receives weather requests from the view.
 */
public class WeatherController {

    private final WeatherInputBoundary weatherInteractor;

    public WeatherController(WeatherInputBoundary weatherInteractor) {
        this.weatherInteractor = weatherInteractor;
    }

    /**
     * Executes the weather use case.
     *
     * @param city city entered by the user
     * @param selectedDate date selected from the calendar
     */
    public void execute(String city, LocalDate selectedDate) {
        WeatherInputData inputData =
                new WeatherInputData(city, selectedDate);
        weatherInteractor.execute(inputData);
    }
}
