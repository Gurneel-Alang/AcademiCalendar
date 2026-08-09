package use_case.weather.advice;

import java.util.List;

import entity.weather.ForecastSlot;

/**
 * Generates advice for windy weather.
 */
public final class WindAdviceStrategy
        implements WeatherAdviceStrategy {

    private static final double STRONG_WIND_SPEED = 10.0;

    @Override
    public String generate(
            List<ForecastSlot> forecastSlots) {

        double highestWindSpeed =
                Double.NEGATIVE_INFINITY;

        for (ForecastSlot slot : forecastSlots) {
            highestWindSpeed = Math.max(
                    highestWindSpeed,
                    slot.getWindSpeed()
            );
        }

        if (highestWindSpeed
                < STRONG_WIND_SPEED) {
            return "";
        }

        return String.format(
                "Strong winds expected (%.1f m/s). "
                        + "Take care outdoors.",
                highestWindSpeed
        );
    }
}