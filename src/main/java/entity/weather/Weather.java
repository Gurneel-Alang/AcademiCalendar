package entity.weather;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Represents the weather forecast for one city on one date.
 */
public final class Weather {

    private final String city;
    private final LocalDate date;
    private final List<ForecastSlot> forecastSlots;

    /**
     * Creates weather information for a city and date.
     *
     * @param city display name of the city
     * @param date selected forecast date
     * @param forecastSlots detailed forecasts throughout the day
     */
    public Weather(
            String city,
            LocalDate date,
            List<ForecastSlot> forecastSlots) {

        this.city = Objects.requireNonNull(city);
        this.date = Objects.requireNonNull(date);

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

    public List<ForecastSlot> getForecastSlots() {
        return forecastSlots;
    }

    public ForecastSlot getRepresentativeSlot() {
        if (forecastSlots.isEmpty()) {
            throw new IllegalStateException(
                    "Weather has no forecast slots."
            );
        }

        return forecastSlots.stream()
                .min(
                        Comparator.comparingLong(
                                slot -> distanceFromNoon(
                                        slot.getDateTime().toLocalTime()
                                )
                        )
                )
                .orElseThrow(
                        () -> new IllegalStateException(
                                "Weather has no forecast slots."
                        )
                );
    }

    private long distanceFromNoon(LocalTime time) {
        return Math.abs(
                Duration.between(
                        time,
                        LocalTime.NOON
                ).toMinutes()
        );
    }}