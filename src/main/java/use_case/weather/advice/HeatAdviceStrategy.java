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

        for (ForecastSlot slot : forecastSlots) {
            if (slot.getTemperature()
                    >= HOT_TEMPERATURE) {

                return "High temperatures are expected today. "
                        + "Remember to stay hydrated.";
            }
        }

        return "";
    }
}