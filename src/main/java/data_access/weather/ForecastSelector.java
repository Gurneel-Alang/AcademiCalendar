package data_access.weather;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import data_access.weather.dto.ForecastResponse;
import data_access.weather.dto.ForecastResponse.ForecastEntry;
import data_access.weather.dto.ForecastResponse.WeatherDescription;
import data_access.weather.dto.LocationResponse;
import entity.weather.ForecastSlot;
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
                if (response == null
                        || response.getList() == null
                        || response.getCity() == null) {

                    throw new IOException(
                            "Invalid forecast response."
                    );
                }

                final ZoneOffset cityOffset =
                        ZoneOffset.ofTotalSeconds(
                                response.getCity().getTimezone()
                        );

                final List<ForecastSlot> forecastSlots = new ArrayList<>();

                for (ForecastResponse.ForecastEntry entry
                        : response.getList()) {

                    final LocalDateTime localDateTime =
                            Instant.ofEpochSecond(
                                            entry.getTimestamp()
                                    )
                                    .atOffset(cityOffset)
                                    .toLocalDateTime();

                    if (localDateTime.toLocalDate()
                            .equals(selectedDate)) {

                        forecastSlots.add(
                                createForecastSlot(
                                        entry,
                                        localDateTime
                                )
                        );
                    }
                }

                if (forecastSlots.isEmpty()) {
                    throw new IOException(
                            "Weather is not available for the selected date."
                    );
                }

                forecastSlots.sort(
                        Comparator.comparing(
                                ForecastSlot::getDateTime
                        )
                );

                final String displayCity =
                        response.getCity().getName()
                                + ", "
                                + response.getCity().getCountry();

                return new Weather(
                        displayCity,
                        selectedDate,
                        forecastSlots
                );
            }

            /**
             * Converts one OpenWeather forecast entry into a domain ForecastSlot.
             *
             * @param entry OpenWeather forecast entry
             * @param localDateTime forecast time in the city's local time
             * @return application forecast slot
             * @throws IOException if required forecast data is missing
             */
            private ForecastSlot createForecastSlot(
                    ForecastResponse.ForecastEntry entry,
                    LocalDateTime localDateTime) throws IOException {

                if (entry.getMain() == null
                        || entry.getWeather() == null
                        || entry.getWeather().isEmpty()
                        || entry.getWind() == null) {

                    throw new IOException(
                            "Incomplete weather forecast data."
                    );
                }

                final ForecastResponse.WeatherDescription
                        weatherDescription =
                        entry.getWeather().get(0);

                return new ForecastSlot(
                        localDateTime,
                        entry.getMain().getTemp(),
                        entry.getMain().getFeelsLike(),
                        weatherDescription.getMain(),
                        weatherDescription.getDescription(),
                        entry.getMain().getHumidity(),
                        entry.getWind().getSpeed(),
                        entry.getPop()
                );
            }
        }