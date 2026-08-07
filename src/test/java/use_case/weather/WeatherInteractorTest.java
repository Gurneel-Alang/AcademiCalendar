package use_case.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Test;

import entity.weather.Weather;

public class WeatherInteractorTest {

    @Test
    public void successTest() {
        final LocalDate date =
                LocalDate.of(2026, 8, 8);

        final Weather expectedWeather =
                new Weather(
                        "Toronto, Ontario, CA",
                        date,
                        24.0,
                        25.0,
                        "Clear",
                        "clear sky",
                        55,
                        3.2
                );

        final WeatherDataAccessInterface fakeDao =
                new WeatherDataAccessInterface() {
                    @Override
                    public Weather getWeather(
                            String city,
                            LocalDate requestedDate) {

                        assertEquals("Toronto", city);
                        assertEquals(date, requestedDate);

                        return expectedWeather;
                    }
                };

        final WeatherOutputBoundary presenter =
                new WeatherOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            WeatherOutputData outputData) {

                        assertEquals(
                                "Toronto, Ontario, CA",
                                outputData.getCity()
                        );

                        assertEquals(
                                date,
                                outputData.getSelectedDate()
                        );

                        assertEquals(
                                24.0,
                                outputData.getTemperature(),
                                0.001
                        );
                    }

                    @Override
                    public void prepareFailView(String error) {
                        fail(
                                "Unexpected failure: " + error
                        );
                    }
                };

        final WeatherInputBoundary interactor =
                new WeatherInteractor(
                        fakeDao,
                        presenter
                );

        interactor.execute(
                new WeatherInputData(
                        "Toronto",
                        date
                )
        );
    }

    @Test
    public void emptyCityFailureTest() {
        final WeatherDataAccessInterface fakeDao =
                new FailingIfCalledWeatherDao();

        final WeatherOutputBoundary presenter =
                new WeatherOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            WeatherOutputData outputData) {
                        fail("Success was not expected.");
                    }

                    @Override
                    public void prepareFailView(String error) {
                        assertEquals(
                                "Please enter a city.",
                                error
                        );
                    }
                };

        final WeatherInputBoundary interactor =
                new WeatherInteractor(
                        fakeDao,
                        presenter
                );

        interactor.execute(
                new WeatherInputData(
                        "   ",
                        LocalDate.of(2026, 8, 8)
                )
        );
    }

    @Test
    public void nullDateFailureTest() {
        final WeatherDataAccessInterface fakeDao =
                new FailingIfCalledWeatherDao();

        final WeatherOutputBoundary presenter =
                new WeatherOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            WeatherOutputData outputData) {
                        fail("Success was not expected.");
                    }

                    @Override
                    public void prepareFailView(String error) {
                        assertEquals(
                                "Please select a date.",
                                error
                        );
                    }
                };

        final WeatherInputBoundary interactor =
                new WeatherInteractor(
                        fakeDao,
                        presenter
                );

        interactor.execute(
                new WeatherInputData(
                        "Toronto",
                        null
                )
        );
    }

    @Test
    public void dataAccessFailureTest() {
        final WeatherDataAccessInterface fakeDao =
                new WeatherDataAccessInterface() {
                    @Override
                    public Weather getWeather(
                            String city,
                            LocalDate date)
                            throws IOException {

                        throw new IOException(
                                "Weather service unavailable."
                        );
                    }
                };

        final WeatherOutputBoundary presenter =
                new WeatherOutputBoundary() {
                    @Override
                    public void prepareSuccessView(
                            WeatherOutputData outputData) {
                        fail("Success was not expected.");
                    }

                    @Override
                    public void prepareFailView(String error) {
                        assertEquals(
                                "Weather service unavailable.",
                                error
                        );
                    }
                };

        final WeatherInputBoundary interactor =
                new WeatherInteractor(
                        fakeDao,
                        presenter
                );

        interactor.execute(
                new WeatherInputData(
                        "Toronto",
                        LocalDate.of(2026, 8, 8)
                )
        );
    }

    private static final class FailingIfCalledWeatherDao
            implements WeatherDataAccessInterface {

        @Override
        public Weather getWeather(
                String city,
                LocalDate date) {

            fail(
                    "DAO should not be called "
                            + "when input is invalid."
            );

            return null;
        }
    }
}