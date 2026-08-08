package interface_adapter.weather;

import use_case.weather.WeatherOutputBoundary;
import use_case.weather.WeatherOutputData;

/**
 * Converts weather output into values suitable for display.
 */
public class WeatherPresenter implements WeatherOutputBoundary {

    private final WeatherViewModel weatherViewModel;

    public WeatherPresenter(WeatherViewModel weatherViewModel) {
        this.weatherViewModel = weatherViewModel;
    }

    @Override
    public void prepareSuccessView(WeatherOutputData outputData) {
        final WeatherState state = weatherViewModel.getState();

        state.setCity(outputData.getCity());
        state.setDate(outputData.getSelectedDate());

        state.setTemperature(
                String.format("%.1f°C", outputData.getTemperature())
        );

        state.setFeelsLike(
                String.format(
                        "Feels like %.1f°C",
                        outputData.getFeelsLike()
                )
        );

        state.setCondition(outputData.getCondition());
        state.setDescription(outputData.getDescription());

        state.setHumidity(
                "Humidity: " + outputData.getHumidity() + "%"
        );

        state.setWindSpeed(
                String.format(
                        "Wind: %.1f m/s",
                        outputData.getWindSpeed()
                )
        );

        state.setError("");

        weatherViewModel.setState(state);
        weatherViewModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(
            String errorMessage) {

        /*
         * A new empty state removes the previous
         * successful weather result.
         */
        final WeatherState state =
                new WeatherState();

        if (errorMessage == null
                || errorMessage.isBlank()) {

            state.setError(
                    "Weather information could not be loaded."
            );
        }
        else {
            state.setError(errorMessage);
        }

        weatherViewModel.setState(state);
        weatherViewModel.firePropertyChanged();
    }
}
