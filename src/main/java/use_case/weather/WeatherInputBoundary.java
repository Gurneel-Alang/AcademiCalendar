package use_case.weather;

public interface WeatherInputBoundary {
    /**
     * Execute the weather use case.
     *
     * @param inputData the weather request information
     */
    void execute(WeatherInputData inputData);
}
