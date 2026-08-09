package interface_adapter.weather;

import use_case.weather.ForecastSlotOutputData;
import use_case.weather.WeatherOutputBoundary;
import use_case.weather.WeatherOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Converts weather output into values suitable for display.
 */
public class WeatherPresenter implements WeatherOutputBoundary {

    private final WeatherViewModel weatherViewModel;
    public WeatherPresenter(WeatherViewModel weatherViewModel) {
        this.weatherViewModel = weatherViewModel;}

    @Override
    public void prepareSuccessView(WeatherOutputData outputData) {
        final WeatherState state = weatherViewModel.getState();
        final ForecastSlotOutputData representativeSlot =
                outputData.getRepresentativeSlot();
        state.setCity(outputData.getCity());
        state.setDate(outputData.getDate());
        state.setTemperature(
                String.format(
                        "%.1f°C",
                        representativeSlot.getTemperature()
                )
        );

        state.setFeelsLike(
                String.format(
                        "Feels like %.1f°C",
                        representativeSlot.getFeelsLike()
                ));
        state.setCondition(
                representativeSlot.getCondition()
        );

        state.setDescription(
                representativeSlot.getDescription()
        );

        state.setHumidity(
                "Humidity: "
                        + representativeSlot.getHumidity()
                        + "%"
        );

        state.setWindSpeed(
                String.format(
                        "Wind: %.1f m/s",
                        representativeSlot.getWindSpeed()
                )
        );

        state.setAdvice(outputData.getAdvice());
        state.setTemperaturePoints(
                createTemperaturePoints(
                        outputData.getForecastSlots()
                )
        );
        state.setError("");

        weatherViewModel.setState(state);
        weatherViewModel.firePropertyChanged();
    }

    private List<TemperaturePoint> createTemperaturePoints(List<ForecastSlotOutputData> forecastSlots) {
            final List<TemperaturePoint> points =
                    new ArrayList<>();
            for (ForecastSlotOutputData forecastSlot : forecastSlots) {

                points.add(new TemperaturePoint(
                                forecastSlot
                                        .getDateTime()
                                        .toLocalTime(),
                                forecastSlot.getTemperature()
                        )
                );}
            return points;
    }

    @Override
    public void prepareFailView(String errorMessage) {
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