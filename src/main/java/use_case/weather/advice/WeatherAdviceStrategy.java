package use_case.weather.advice;
import java.util.List;

import entity.weather.ForecastSlot;

/**
 * Strategy for generating one type of weather advice.
 */
public interface WeatherAdviceStrategy {

    /**
     * Generates advice from forecast data.
     *
     * @param forecastSlots forecast slots for the selected day
     * @return advice, or an empty string if no advice is needed
     */
    String generate(List<ForecastSlot> forecastSlots);
}