package data_access.weather.dto;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ForecastResponse {

    @SerializedName("list")
    private List<ForecastEntry> forecasts;

    private ForecastCity city;

    public List<ForecastEntry> getForecasts() {
        return forecasts;
    }

    public ForecastCity getCity() {
        return city;
    }

    public static class ForecastEntry {
        @SerializedName("dt")
        private long timestamp;

        private ForecastMain main;
        private List<WeatherDescription> weather;
        private WindData wind;

        public long getTimestamp() {
            return timestamp;
        }

        public ForecastMain getMain() {
            return main;
        }

        public List<WeatherDescription> getWeather() {
            return weather;
        }

        public WindData getWind() {
            return wind;
        }
    }

    public static class ForecastMain {
        @SerializedName("temp")
        private double temperature;

        @SerializedName("feels_like")
        private double feelsLike;

        private int humidity;

        public double getTemperature() {
            return temperature;
        }

        public double getFeelsLike() {
            return feelsLike;
        }

        public int getHumidity() {
            return humidity;
        }
    }

    public static class ForecastCity {
        private int timezone;

        public int getTimezone() {
            return timezone;
        }
    }

    public static class WeatherDescription {
        @SerializedName("main")
        private String condition;

        private String description;

        public String getCondition() {
            return condition;
        }

        public String getDescription() {
            return description;
        }
    }

    public static class WindData {
        private double speed;

        public double getSpeed() {
            return speed;
        }
    }
}