package data_access.weather;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;

import data_access.weather.dto.ForecastResponse;
import data_access.weather.dto.ForecastResponse.ForecastEntry;
import data_access.weather.dto.ForecastResponse.WeatherDescription;
import data_access.weather.dto.LocationResponse;
import entity.weather.Weather;

/**
 * Selects a representative forecast for a requested date.
 */
public class ForecastSelector {

    private static final LocalTime REPRESENTATIVE_TIME =
            LocalTime.NOON;

    /**
     * Selects the forecast closest to local noon on the selected date.
     *
     * @param response parsed forecast response
     * @param location parsed location information
     * @param selectedDate date selected in the calendar
     * @return weather entity
     * @throws IOException if no valid forecast is available
     */
    public Weather selectWeather(
            ForecastResponse response,
            LocationResponse location,
            LocalDate selectedDate) throws IOException {

        validateResponse(response, location, selectedDate);

        final int timezoneOffset =
                response.getCity().getTimezone();

        final ForecastEntry selectedForecast =
                response.getForecasts()
                        .stream()
                        .filter(this::hasRequiredData)
                        .filter(entry ->
                                selectedDate.equals(
                                        toLocalDate(
                                                entry.getTimestamp(),
                                                timezoneOffset
                                        )
                                )
                        )
                        .min(Comparator.comparingLong(entry ->
                                distanceFromNoon(
                                        entry.getTimestamp(),
                                        timezoneOffset
                                )
                        ))
                        .orElseThrow(() -> new IOException(
                                "Forecast is not available "
                                        + "for the selected date."
                        ));

        final WeatherDescription weatherDescription =
                selectedForecast.getWeather().get(0);

        return new Weather(
                createLocationName(location),
                selectedDate,
                selectedForecast.getMain().getTemperature(),
                selectedForecast.getMain().getFeelsLike(),
                weatherDescription.getCondition(),
                weatherDescription.getDescription(),
                selectedForecast.getMain().getHumidity(),
                selectedForecast.getWind().getSpeed()
        );
    }

    private void validateResponse(
            ForecastResponse response,
            LocationResponse location,
            LocalDate selectedDate) throws IOException {

        if (location == null) {
            throw new IOException(
                    "Location information is missing."
            );
        }

        if (selectedDate == null) {
            throw new IOException(
                    "A date must be selected."
            );
        }

        if (response == null
                || response.getCity() == null
                || response.getForecasts() == null) {
            throw new IOException(
                    "OpenWeather returned an invalid forecast response."
            );
        }
    }

    private boolean hasRequiredData(ForecastEntry entry) {
        return entry != null
                && entry.getMain() != null
                && entry.getWind() != null
                && entry.getWeather() != null
                && !entry.getWeather().isEmpty();
    }

    private LocalDate toLocalDate(
            long timestamp,
            int timezoneOffset) {

        return LocalDateTime.ofInstant(
                Instant.ofEpochSecond(timestamp),
                ZoneOffset.ofTotalSeconds(timezoneOffset)
        ).toLocalDate();
    }

    private long distanceFromNoon(
            long timestamp,
            int timezoneOffset) {

        final LocalTime forecastTime =
                LocalDateTime.ofInstant(
                        Instant.ofEpochSecond(timestamp),
                        ZoneOffset.ofTotalSeconds(timezoneOffset)
                ).toLocalTime();

        return Math.abs(
                ChronoUnit.MINUTES.between(
                        REPRESENTATIVE_TIME,
                        forecastTime
                )
        );
    }

    private String createLocationName(
            LocationResponse location) {

        final StringBuilder result =
                new StringBuilder(location.getName());

        if (location.getState() != null
                && !location.getState().isBlank()) {
            result.append(", ")
                    .append(location.getState());
        }

        if (location.getCountry() != null
                && !location.getCountry().isBlank()) {
            result.append(", ")
                    .append(location.getCountry());
        }

        return result.toString();
    }
}
