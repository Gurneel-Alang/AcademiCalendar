package data_access.weather.openweather;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

/**
 * Calls the OpenWeather Geocoding and Forecast APIs.
 *
 */
public class OpenWeatherApiClient implements WeatherApiClient {

    private static final String GEOCODING_URL =
            "https://api.openweathermap.org/geo/1.0/direct";

    private static final String FORECAST_URL =
            "https://api.openweathermap.org/data/2.5/forecast";

    private final String apiKey;
    private final HttpClient httpClient;

    public OpenWeatherApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public String fetchLocation(String city) throws IOException {
        validateApiKey();
        final String requestUrl = GEOCODING_URL + "?q=" + encode(city)
                        + "&limit=1"
                        + "&appid=" + encode(apiKey);
        return sendGetRequest(requestUrl);
    }

    @Override
    public String fetchForecast(
            double latitude,
            double longitude) throws IOException {

        validateApiKey();

        final String requestUrl =
                FORECAST_URL
                        + "?lat=" + latitude
                        + "&lon=" + longitude
                        + "&appid=" + encode(apiKey)
                        + "&units=metric";

        return sendGetRequest(requestUrl);
    }

    private String sendGetRequest(String requestUrl)
            throws IOException {
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(requestUrl))
                .GET()
                .build();

        final HttpResponse<String> response;

        try {
            response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
        }
        catch (InterruptedException exception) {
            Thread.currentThread().interrupt();

            throw new IOException(
                    "The weather request was interrupted.",
                    exception
            );
        }

        if (response.statusCode() != 200) {
            throw new IOException(
                    "OpenWeather request failed with HTTP status "
                            + response.statusCode()
                            + "."
            );
        }

        return response.body();
    }

    private String encode(String value) {
        return URLEncoder.encode(
                value,
                StandardCharsets.UTF_8
        );
    }

    private void validateApiKey() throws IOException {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IOException(
                    "The OpenWeather API key is missing."
            );
        }
    }
}
