package use_case.weather.advice;

import java.util.List;

import entity.weather.ForecastSlot;

/**
 * Generates advice for hot weather.
 */
public final class HeatAdviceStrategy
        implements WeatherAdviceStrategy {

    private static final double HOT_TEMPERATURE = 30.0;

    @Override
    public String generate(
            List<ForecastSlot> forecastSlots) {

        double highestTemperature =
                Double.NEGATIVE_INFINITY;

        for (ForecastSlot slot : forecastSlots) {
            highestTemperature = Math.max(
                    highestTemperature,
                    slot.getTemperature()
            );
        }

        if (highestTemperature
                < HOT_TEMPERATURE) {
            return "";
        }

        return String.format(
                "High temperature expected (%.0f°C). "
                        + "Remember to stay hydrated.",
                highestTemperature
        );
    }
}