package data_access.weather.dto;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the response from the OpenWeather forecast API.
 */
public final class ForecastResponse {

    private List<ForecastEntry> list;
    private CityData city;

    public List<ForecastEntry> getList() {
        return list;
    }

    public CityData getCity() {
        return city;
    }

    /**
     * Represents one forecast entry from OpenWeather.
     */
    public static final class ForecastEntry {

        @SerializedName("dt")
        private long timestamp;

        private MainWeatherData main;
        private List<WeatherDescription> weather;
        private WindData wind;

        /**
         * Probability of precipitation.
         *
         * <p>OpenWeather returns this value between 0.0 and 1.0.</p>
         */
        private double pop;

        public long getTimestamp() {
            return timestamp;
        }

        public MainWeatherData getMain() {
            return main;
        }

        public List<WeatherDescription> getWeather() {
            return weather;
        }

        public WindData getWind() {
            return wind;
        }

        public double getPop() {
            return pop;
        }
    }

    /**
     * Represents temperature and humidity data.
     */
    public static final class MainWeatherData {

        private double temp;

        @SerializedName("feels_like")
        private double feelsLike;

        private int humidity;

        public double getTemp() {
            return temp;
        }

        public double getFeelsLike() {
            return feelsLike;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    /**
     * Represents weather condition information.
     */
    public static final class WeatherDescription {

        private String main;
        private String description;

        public String getMain() {
            return main;
        }

        public String getDescription() {
            return description;
        }
    }

    /**
     * Represents wind information.
     */
    public static final class WindData {

        private double speed;

        public double getSpeed() {
            return speed;
        }
    }

    /**
     * Represents city information included in the forecast response.
     */
    public static final class CityData {

        private String name;
        private String country;
        private int timezone;

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public int getTimezone() {
            return timezone;
        }
    }
}