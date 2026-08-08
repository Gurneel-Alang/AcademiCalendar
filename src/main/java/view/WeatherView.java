package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.*;

import interface_adapter.weather.WeatherController;
import interface_adapter.weather.WeatherState;
import interface_adapter.weather.WeatherViewModel;

/**
 * Displays the weather-search form and weather result.
 */
public class WeatherView extends JPanel implements PropertyChangeListener {

    private static final int INFORMATION_ROWS = 8;
    private static final int INFORMATION_COLUMNS = 1;
    private static final int TEXT_FIELD_COLUMNS = 15;

    private final WeatherViewModel weatherViewModel;
    private final WeatherController weatherController;

    private final JTextField cityField =
            new JTextField(TEXT_FIELD_COLUMNS);
    private final JTextField dateField =
            new JTextField(TEXT_FIELD_COLUMNS);

    private final JLabel locationLabel =
            new JLabel("Location:");
    private final JLabel dateLabel =
            new JLabel("Date:");
    private final JLabel temperatureLabel =
            new JLabel("Temperature:");
    private final JLabel feelsLikeLabel =
            new JLabel("Feels like:");
    private final JLabel conditionLabel =
            new JLabel("Condition:");
    private final JLabel descriptionLabel =
            new JLabel("Description:");
    private final JLabel humidityLabel =
            new JLabel("Humidity:");
    private final JLabel windSpeedLabel =
            new JLabel("Wind:");

    private final JLabel errorLabel =
            new JLabel();

    private final JButton searchButton =
            new JButton("Search");

    public WeatherView(
            WeatherViewModel weatherViewModel,
            WeatherController weatherController) {
        this.weatherViewModel = weatherViewModel;
        this.weatherController = weatherController;
        weatherViewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout());
        setBorder(
                BorderFactory.createTitledBorder("Weather")
        );

        dateField.setText(LocalDate.now().toString());

        final JPanel inputPanel =
                createInputPanel();

        final JPanel informationPanel =
                createInformationPanel();

        add(inputPanel, BorderLayout.NORTH);
        add(informationPanel, BorderLayout.SOUTH);
        add(errorLabel, BorderLayout.CENTER);
    }

    private JPanel createInputPanel() {
        final JPanel inputPanel =
                new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("City:"));
        inputPanel.add(cityField);
        inputPanel.add(new JLabel("Date (YYYY-MM-DD):"));
        inputPanel.add(dateField);
        inputPanel.add(new JLabel());
        inputPanel.add(searchButton);
        searchButton.addActionListener(
                event -> requestWeather()
        );
        return inputPanel;
    }

    private JPanel createInformationPanel() {
        final JPanel informationPanel =
                new JPanel(new GridLayout(
                                INFORMATION_ROWS,
                                INFORMATION_COLUMNS)
                );
        informationPanel.add(locationLabel);
        informationPanel.add(dateLabel);
        informationPanel.add(temperatureLabel);
        informationPanel.add(feelsLikeLabel);
        informationPanel.add(conditionLabel);
        informationPanel.add(descriptionLabel);
        informationPanel.add(humidityLabel);
        informationPanel.add(windSpeedLabel);

        return informationPanel;
    }

    private void requestWeather() {
        final LocalDate selectedDate;
        try {
            selectedDate =
                    LocalDate.parse(dateField.getText().trim());
        }
        catch (DateTimeParseException exception) {
            errorLabel.setText("Please enter the date as YYYY-MM-DD");
            return;
        }
        final String city = cityField.getText().trim();
        if (city.isEmpty()) {
            errorLabel.setText("Please enter a city.");
            return;
        }
        errorLabel.setText("Loading weather...");
        searchButton.setEnabled(false);

        final javax.swing.SwingWorker<Void, Void> worker =
                new javax.swing.SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        weatherController.execute(
                                city,
                                selectedDate);
                        return null;
                    }

                    @Override
                    protected void done() {
                        searchButton.setEnabled(true);
                    }
        };
        worker.execute();
    }

    /**
     * Displays an empty weather result area with an
     * optional status or error message.
     *
     * @param message status/error message, or empty string
     */
    private void showEmptyWeather(
            String message) {

        locationLabel.setText("Location:");
        dateLabel.setText("Date:");
        temperatureLabel.setText("Temperature:");
        feelsLikeLabel.setText("Feels like:");
        conditionLabel.setText("Condition:");
        descriptionLabel.setText("Description:");
        humidityLabel.setText("Humidity:");
        windSpeedLabel.setText("Wind:");
        if (message == null) {
            errorLabel.setText("");
        }
        else {
            errorLabel.setText(message);
        }
    }

    /**
     * Displays a successful weather result.
     * @param state successful weather state
     */
    private void showWeatherInformation(
            WeatherState state) {
        errorLabel.setText("");
        locationLabel.setText(
                "Location: " + state.getCity()
        );
        dateLabel.setText(
                "Date: " + state.getDate());
        temperatureLabel.setText(
                "Temperature: "
                        + state.getTemperature());
        feelsLikeLabel.setText(
                state.getFeelsLike()
        );
        conditionLabel.setText(
                "Condition: "
                        + state.getCondition()
        );
        descriptionLabel.setText(
                "Description: "
                        + state.getDescription());
        humidityLabel.setText(
                state.getHumidity());
        windSpeedLabel.setText(
                state.getWindSpeed());
    }

    /**
     * Allows the calendar to supply the selected date.
     *
     * @param selectedDate selected calendar date
     */
    public void setSelectedDate(
            LocalDate selectedDate) {
        if (selectedDate != null) {
            dateField.setText(
                    selectedDate.toString()
            );
        }
    }

    @Override
    public void propertyChange(
            PropertyChangeEvent event) {
        /*
         * The Presenter may fire this event from
         * SwingWorker's background thread.
         */
        if (!SwingUtilities.isEventDispatchThread()) {
            SwingUtilities.invokeLater(
                    this::updateWeatherInformation
            );
            return;
        }
        updateWeatherInformation();
    }

    private void updateWeatherInformation() {
        final WeatherState state =
                weatherViewModel.getState();
        if (state.getError() != null
                && !state.getError().isBlank()) {
            showEmptyWeather(
                    state.getError());
            return;
        }
        showWeatherInformation(state);
    }
}
