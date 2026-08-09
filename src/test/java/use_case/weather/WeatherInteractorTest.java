package use_case.weather;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import entity.weather.ForecastSlot;
import entity.weather.Weather;
import use_case.weather.advice.WeatherAdviceGenerator;

/**
 * Unit tests for the weather use case interactor.
 */
public class WeatherInteractorTest {

    private static final LocalDate DATE = LocalDate.of(2026, 8, 9);

    @Test
    public void successTest() {
        final Weather weather = createWeather();
        final RecordingPresenter presenter = new RecordingPresenter();

        final WeatherDataAccessInterface fakeDao = (city, date) -> {
            assertEquals("Toronto", city);
            assertEquals(DATE, date);
            return weather;
        };

        createInteractor(fakeDao, presenter).execute(
                new WeatherInputData("  Toronto  ", DATE)
        );

        assertNull(presenter.error);
        assertNotNull(presenter.output);
        assertEquals("Toronto, Ontario, CA", presenter.output.getCity());
        assertEquals(DATE, presenter.output.getDate());
        assertEquals("Bring an umbrella.", presenter.output.getAdvice());
        assertEquals(2, presenter.output.getForecastSlots().size());

        final ForecastSlotOutputData representative =
                presenter.output.getRepresentativeSlot();

        assertEquals(LocalDateTime.of(2026, 8, 9, 12, 0),
                representative.getDateTime());
        assertEquals(24.0, representative.getTemperature(), 0.001);
        assertEquals(25.0, representative.getFeelsLike(), 0.001);
        assertEquals("Rain", representative.getCondition());
        assertEquals("light rain", representative.getDescription());
        assertEquals(70, representative.getHumidity());
        assertEquals(4.5, representative.getWindSpeed(), 0.001);
        assertEquals(0.8,
                representative.getPrecipitationProbability(), 0.001);
    }

    @Test
    public void nullInputFailureTest() {
        assertFailure(null, "Weather request is required.");
    }

    @Test
    public void nullCityFailureTest() {
        assertFailure(
                new WeatherInputData(null, DATE),
                "Please enter a city."
        );
    }

    @Test
    public void blankCityFailureTest() {
        assertFailure(
                new WeatherInputData("   ", DATE),
                "Please enter a city."
        );
    }

    @Test
    public void nullDateFailureTest() {
        assertFailure(
                new WeatherInputData("Toronto", null),
                "Please select a date."
        );
    }

    @Test
    public void dataAccessFailureTest() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final WeatherDataAccessInterface failingDao = (city, date) -> {
            throw new IOException("Weather service unavailable.");
        };

        createInteractor(failingDao, presenter).execute(
                new WeatherInputData("Toronto", DATE)
        );

        assertNull(presenter.output);
        assertEquals("Weather service unavailable.", presenter.error);
    }

    private void assertFailure(
            WeatherInputData inputData,
            String expectedError) {

        final RecordingPresenter presenter = new RecordingPresenter();
        final WeatherDataAccessInterface unusedDao = (city, date) -> {
            fail("DAO should not be called for invalid input.");
            return null;
        };

        createInteractor(unusedDao, presenter).execute(inputData);

        assertNull(presenter.output);
        assertEquals(expectedError, presenter.error);
    }

    private WeatherInputBoundary createInteractor(
            WeatherDataAccessInterface dao,
            WeatherOutputBoundary presenter) {

        final WeatherAdviceGenerator adviceGenerator =
                new WeatherAdviceGenerator(List.of(
                        slots -> "Bring an umbrella."
                ));

        return new WeatherInteractor(
                dao,
                presenter,
                adviceGenerator
        );
    }

    private Weather createWeather() {
        final ForecastSlot morning = new ForecastSlot(
                LocalDateTime.of(2026, 8, 9, 9, 0),
                20.0, 21.0, "Clouds", "cloudy",
                60, 2.0, 0.1
        );

        final ForecastSlot noon = new ForecastSlot(
                LocalDateTime.of(2026, 8, 9, 12, 0),
                24.0, 25.0, "Rain", "light rain",
                70, 4.5, 0.8
        );

        return new Weather(
                "Toronto, Ontario, CA",
                DATE,
                List.of(morning, noon)
        );
    }

    private static final class RecordingPresenter
            implements WeatherOutputBoundary {

        private WeatherOutputData output;
        private String error;

        @Override
        public void prepareSuccessView(WeatherOutputData outputData) {
            output = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            error = errorMessage;
        }
    }
}
