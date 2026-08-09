package data_access.weather;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import data_access.weather.dto.ForecastResponse;
import data_access.weather.dto.LocationResponse;

/**
 * Converts OpenWeather JSON responses into data-transfer objects（dto）.
 */
public class OpenWeatherJsonParser {

    private final Gson gson;

    public OpenWeatherJsonParser() {
        this.gson = new Gson();
    }

    /**
     * Parses the Geocoding API response.
     *
     * @param json raw location JSON
     * @return first matching location
     * @throws IOException if the response is invalid or empty
     */
    public LocationResponse parseLocation(String json)
            throws IOException {

        try {
            final LocationResponse[] locations =
                    gson.fromJson(
                            json,
                            LocationResponse[].class
                    );

            if (locations == null || locations.length == 0) {
                throw new IOException(
                        "The city could not be found."
                );
            }

            return locations[0];
        }
        catch (JsonParseException exception) {
            throw new IOException(
                    "The location response could not be parsed.",
                    exception
            );
        }
    }

    /**
     * Parses the Forecast API response.
     *
     * @param json raw forecast JSON
     * @return parsed forecast response
     * @throws IOException if the response cannot be parsed
     */
    public ForecastResponse parseForecast(String json)
            throws IOException {
        try {
            final ForecastResponse response =
                    gson.fromJson(
                            json,
                            ForecastResponse.class
                    );

            if (response == null) {
                throw new IOException(
                        "OpenWeather returned an empty forecast response."
                );
            }
            return response;
        }
        catch (JsonParseException exception) {
            throw new IOException(
                    "The forecast response could not be parsed.",
                    exception
            );
        }
    }
}
