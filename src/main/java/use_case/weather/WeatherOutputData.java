package use_case.weather;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Output data produced by the weather use case.
 */
public final class WeatherOutputData {

    private final String city;
    private final LocalDate date;
    private final ForecastSlotOutputData representativeSlot;
    private final String advice;
    private final List<ForecastSlotOutputData> forecastSlots;

    public WeatherOutputData(
            String city,
            LocalDate date,
            ForecastSlotOutputData representativeSlot,
            String advice,
            List<ForecastSlotOutputData> forecastSlots) {

        this.city = Objects.requireNonNull(city);
        this.date = Objects.requireNonNull(date);
        this.representativeSlot =
                Objects.requireNonNull(representativeSlot);
        this.advice = Objects.requireNonNull(advice);

        this.forecastSlots = Collections.unmodifiableList(
                new ArrayList<>(
                        Objects.requireNonNull(forecastSlots)
                )
        );
    }

    public String getCity() {
        return city;
    }

    public LocalDate getDate() {
        return date;
    }

    public ForecastSlotOutputData getRepresentativeSlot() {
        return representativeSlot;
    }

    public String getAdvice() {
        return advice;
    }

    public List<ForecastSlotOutputData> getForecastSlots() {
        return forecastSlots;
    }
}