package use_case.weather;

public interface WeatherOutputBoundary {
    /**
     * Prepare the success view.
     *
     * @param outputData the weather information
     */
    void prepareSuccessView(WeatherOutputData outputData);

    /**
     * Prepare the failure view.
     *
     * @param errorMessage the error message
     */
    void prepareFailView(String errorMessage);
}
