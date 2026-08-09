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

        for (ForecastSlot slot : forecastSlots) {
            if (slot.getWindSpeed()
                    >= STRONG_WIND_SPEED) {

                return "Strong winds are expected today. "
                        + "Take care when travelling outdoors.";
            }
        }

        return "";
    }
}