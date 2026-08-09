package use_case.weather.advice;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import entity.weather.ForecastSlot;

/**
 * Generates rain-related weather advice.
 */
public final class RainAdviceStrategy
        implements WeatherAdviceStrategy {

    private static final double RAIN_THRESHOLD = 0.5;

    private static final int FORECAST_INTERVAL_HOURS = 3;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    @Override
    public String generate(
            List<ForecastSlot> forecastSlots) {

        ForecastSlot firstRainSlot = null;
        ForecastSlot lastRainSlot = null;

        for (ForecastSlot slot : forecastSlots) {
            if (isRainExpected(slot)) {

                if (firstRainSlot == null) {
                    firstRainSlot = slot;
                }

                lastRainSlot = slot;
            }
        }

        if (firstRainSlot == null) {
            return "";
        }

        final LocalTime startTime = firstRainSlot.getDateTime().toLocalTime();

        final LocalTime endTime =
                lastRainSlot
                        .getDateTime()
                        .toLocalTime()
                        .plusHours(
                                FORECAST_INTERVAL_HOURS
                        );

        return "Rain expected "
                + startTime.format(TIME_FORMATTER) + "–"
                + endTime.format(TIME_FORMATTER)
                + ". Bring an umbrella.";
    }

    private boolean isRainExpected(
            ForecastSlot slot) {

        final String condition =
                slot.getCondition().toLowerCase();

        return condition.contains("rain")
                || slot.getPrecipitationProbability()
                >= RAIN_THRESHOLD;
    }
}