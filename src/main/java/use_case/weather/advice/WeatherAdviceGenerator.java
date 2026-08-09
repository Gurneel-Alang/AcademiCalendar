package use_case.weather.advice;

import java.util.ArrayList;
import java.util.List;

import entity.weather.ForecastSlot;

/**
 * Combines weather advice from multiple strategies.
 */
public final class WeatherAdviceGenerator {

    private final List<WeatherAdviceStrategy> strategies;

    /**
     * Creates a weather advice generator.
     *
     * @param strategies advice strategies to apply
     */
    public WeatherAdviceGenerator(
            List<WeatherAdviceStrategy> strategies) {

        this.strategies =
                new ArrayList<>(strategies);
    }

    /**
     * Generates all applicable advice.
     *
     * @param forecastSlots forecast slots
     * @return combined advice
     */
    public String generate(
            List<ForecastSlot> forecastSlots) {

        final List<String> adviceMessages =
                new ArrayList<>();

        for (WeatherAdviceStrategy strategy
                : strategies) {

            final String advice =
                    strategy.generate(
                            forecastSlots
                    );

            if (advice != null
                    && !advice.isBlank()) {

                adviceMessages.add(advice);
            }
        }

        if (adviceMessages.isEmpty()) {
            return "No special weather precautions are needed today.";
        }

        return String.join(
                " ",
                adviceMessages
        );
    }
}