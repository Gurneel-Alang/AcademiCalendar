package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import interface_adapter.weather.WeatherController;
import interface_adapter.weather.WeatherState;
import interface_adapter.weather.WeatherViewModel;

/**
 * Displays the weather-search form and weather result.
 */
public class WeatherView extends JPanel
        implements PropertyChangeListener {

    private static final int INFORMATION_ROWS = 8;
    private static final int INFORMATION_COLUMNS = 1;
    private static final int TEXT_FIELD_COLUMNS = 15;

    private static final int ADVICE_ROWS = 3;
    private static final int ADVICE_COLUMNS = 30;

    private static final float ADVICE_FONT_SIZE = 13f;

    private final WeatherViewModel weatherViewModel;
    private final WeatherController weatherController;

    private final TemperatureChartPanel temperatureChartPanel =
            new TemperatureChartPanel();

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

    private final JTextArea adviceArea =
            new JTextArea(
                    ADVICE_ROWS,
                    ADVICE_COLUMNS
            );

    private final JLabel errorLabel =
            new JLabel();

    private final JButton searchButton =
            new JButton("Search");

    /**
     * Creates the weather view.
     *
     * @param weatherViewModel weather view model
     * @param weatherController weather controller
     */
    public WeatherView(
            WeatherViewModel weatherViewModel,
            WeatherController weatherController) {

        this.weatherViewModel =
                weatherViewModel;

        this.weatherController =
                weatherController;

        weatherViewModel
                .addPropertyChangeListener(this);

        setLayout(new BorderLayout());

        setBorder(
                BorderFactory
                        .createTitledBorder(
                                "Weather"
                        )
        );

        dateField.setText(
                LocalDate.now().toString()
        );

        configureAdviceArea();

        final JPanel inputPanel =
                createInputPanel();

        final JPanel informationPanel =
                createInformationPanel();

        final JPanel weatherDisplayPanel =
                new JPanel(
                        new BorderLayout()
                );

        weatherDisplayPanel.add(
                informationPanel,
                BorderLayout.NORTH
        );

        weatherDisplayPanel.add(
                temperatureChartPanel,
                BorderLayout.CENTER
        );

        add(
                inputPanel,
                BorderLayout.NORTH
        );

        add(
                weatherDisplayPanel,
                BorderLayout.CENTER
        );

        add(
                errorLabel,
                BorderLayout.SOUTH
        );
    }

    private void configureAdviceArea() {

        adviceArea.setEditable(false);

        adviceArea.setOpaque(false);

        adviceArea.setLineWrap(true);

        adviceArea.setWrapStyleWord(true);

        adviceArea.setText("Advice:");

        adviceArea.setFont(
                adviceArea
                        .getFont()
                        .deriveFont(
                                Font.BOLD,
                                ADVICE_FONT_SIZE
                        )
        );
    }

    private JPanel createInputPanel() {

        final JPanel inputPanel =
                new JPanel(
                        new GridLayout(3, 2)
                );

        inputPanel.add(
                new JLabel("City:")
        );

        inputPanel.add(cityField);

        inputPanel.add(
                new JLabel(
                        "Date (YYYY-MM-DD):"
                )
        );

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
                new JPanel(
                        new BorderLayout()
                );

        final JPanel detailsPanel =
                new JPanel(
                        new GridLayout(
                                INFORMATION_ROWS,
                                INFORMATION_COLUMNS
                        )
                );

        detailsPanel.add(locationLabel);
        detailsPanel.add(dateLabel);
        detailsPanel.add(temperatureLabel);
        detailsPanel.add(feelsLikeLabel);
        detailsPanel.add(conditionLabel);
        detailsPanel.add(descriptionLabel);
        detailsPanel.add(humidityLabel);
        detailsPanel.add(windSpeedLabel);

        informationPanel.add(
                detailsPanel,
                BorderLayout.NORTH
        );

        informationPanel.add(
                adviceArea,
                BorderLayout.CENTER
        );

        return informationPanel;
    }

    private void requestWeather() {
        final LocalDate selectedDate;
        try {
            selectedDate =
                    LocalDate.parse(
                            dateField
                                    .getText()
                                    .trim()
                    );
        }
        catch (DateTimeParseException exception) {
            showEmptyWeather(
                    "Please enter the date "
                            + "as YYYY-MM-DD"
            );
            return;
        }

        final String city =
                cityField
                        .getText()
                        .trim();

        if (city.isEmpty()) {
            showEmptyWeather(
                    "Please enter a city.");
            return;
        }

        errorLabel.setText(
                "Loading weather..."
        );

        searchButton.setEnabled(false);

        final SwingWorker<Void, Void> worker =
                new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() {
                        weatherController.execute(
                                city,
                                selectedDate
                        );
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
     * @param message status/error message
     */
    private void showEmptyWeather(
            String message) {

        locationLabel.setText(
                "Location:"
        );

        dateLabel.setText(
                "Date:"
        );

        temperatureLabel.setText(
                "Temperature:"
        );

        feelsLikeLabel.setText(
                "Feels like:"
        );

        conditionLabel.setText(
                "Condition:"
        );

        descriptionLabel.setText(
                "Description:"
        );

        humidityLabel.setText(
                "Humidity:"
        );

        windSpeedLabel.setText(
                "Wind:"
        );

        adviceArea.setText(
                "Advice:"
        );

        temperatureChartPanel.clear();

        if (message == null) {
            errorLabel.setText("");
        }
        else {
            errorLabel.setText(message);
        }
    }

    /**
     * Displays a successful weather result.
     *
     * @param state successful weather state
     */
    private void showWeatherInformation(
            WeatherState state) {

        errorLabel.setText("");

        locationLabel.setText(
                "Location: "
                        + state.getCity()
        );

        dateLabel.setText(
                "Date: "
                        + state.getDate()
        );

        temperatureLabel.setText(
                "Temperature: "
                        + state.getTemperature()
        );

        feelsLikeLabel.setText(
                state.getFeelsLike()
        );

        conditionLabel.setText(
                "Condition: "
                        + state.getCondition()
        );

        descriptionLabel.setText(
                "Description: "
                        + state.getDescription()
        );

        humidityLabel.setText(
                state.getHumidity()
        );

        windSpeedLabel.setText(
                state.getWindSpeed()
        );

        adviceArea.setText(
                "Advice:\n"
                        + state.getAdvice()
        );

        temperatureChartPanel
                .setTemperaturePoints(
                        state.getTemperaturePoints()
                );
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

        if (!SwingUtilities
                .isEventDispatchThread()) {

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
                    state.getError()
            );

            return;
        }

        showWeatherInformation(state);
    }
}