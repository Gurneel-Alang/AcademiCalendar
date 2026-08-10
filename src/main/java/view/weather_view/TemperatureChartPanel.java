package view.weather_view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import interface_adapter.weather.TemperaturePoint;

/**
 * Displays a simple line chart of temperatures
 * throughout the day.
 */
public final class TemperatureChartPanel
        extends JPanel {

    private static final int PREFERRED_WIDTH = 420;
    private static final int PREFERRED_HEIGHT = 240;

    private static final int LEFT_PADDING = 45;
    private static final int RIGHT_PADDING = 20;
    private static final int TOP_PADDING = 30;
    private static final int BOTTOM_PADDING = 40;

    private static final int POINT_RADIUS = 4;

    private static final float TEMPERATURE_FONT_SIZE =
            12f;

    private static final float TIME_FONT_SIZE =
            11f;

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm");

    private List<TemperaturePoint> temperaturePoints =
            new ArrayList<>();

    /**
     * Creates an empty temperature chart.
     */
    public TemperatureChartPanel() {
        setPreferredSize(
                new Dimension(
                        PREFERRED_WIDTH,
                        PREFERRED_HEIGHT
                )
        );
    }

    /**
     * Updates the points displayed on the chart.
     *
     * @param points temperature points to display
     */
    public void setTemperaturePoints(
            List<TemperaturePoint> points) {

        temperaturePoints =
                new ArrayList<>(points);

        repaint();
    }

    /**
     * Removes all points from the chart.
     */
    public void clear() {
        temperaturePoints.clear();
        repaint();
    }

    @Override
    protected void paintComponent(
            Graphics graphics) {

        super.paintComponent(graphics);

        if (temperaturePoints.isEmpty()) {
            return;
        }

        final Graphics2D graphics2D =
                (Graphics2D) graphics.create();

        graphics2D.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        drawChart(graphics2D);

        graphics2D.dispose();
    }

    private void drawChart(
            Graphics2D graphics) {

        final int width = getWidth();
        final int height = getHeight();

        final int chartWidth =
                width
                        - LEFT_PADDING
                        - RIGHT_PADDING;

        final int chartHeight =
                height
                        - TOP_PADDING
                        - BOTTOM_PADDING;

        final double minimumTemperature =
                findMinimumTemperature();

        final double maximumTemperature =
                findMaximumTemperature();

        final double temperatureRange =
                Math.max(
                        1.0,
                        maximumTemperature
                                - minimumTemperature
                );

        drawAxes(
                graphics,
                width,
                height
        );

        int previousX = -1;
        int previousY = -1;

        for (int index = 0;
             index < temperaturePoints.size();
             index++) {

            final TemperaturePoint point =
                    temperaturePoints.get(index);

            final int x =
                    calculateX(
                            index,
                            chartWidth
                    );

            final int y =
                    calculateY(
                            point.getTemperature(),
                            minimumTemperature,
                            temperatureRange,
                            chartHeight
                    );

            if (previousX >= 0) {
                graphics.drawLine(
                        previousX,
                        previousY,
                        x,
                        y
                );
            }

            graphics.fillOval(
                    x - POINT_RADIUS,
                    y - POINT_RADIUS,
                    POINT_RADIUS * 2,
                    POINT_RADIUS * 2
            );

            drawPointLabels(
                    graphics,
                    point,
                    x,
                    y,
                    height
            );

            previousX = x;
            previousY = y;
        }
    }

    private void drawAxes(
            Graphics2D graphics,
            int width,
            int height) {

        graphics.drawLine(
                LEFT_PADDING,
                TOP_PADDING,
                LEFT_PADDING,
                height - BOTTOM_PADDING
        );

        graphics.drawLine(
                LEFT_PADDING,
                height - BOTTOM_PADDING,
                width - RIGHT_PADDING,
                height - BOTTOM_PADDING
        );
    }

    private int calculateX(
            int index,
            int chartWidth) {

        if (temperaturePoints.size() == 1) {
            return LEFT_PADDING
                    + chartWidth / 2;
        }

        return LEFT_PADDING
                + index * chartWidth
                / (temperaturePoints.size() - 1);
    }

    private int calculateY(
            double temperature,
            double minimumTemperature,
            double temperatureRange,
            int chartHeight) {

        final double normalized =
                (temperature
                        - minimumTemperature)
                        / temperatureRange;

        return TOP_PADDING
                + (int) (
                chartHeight
                        * (1.0 - normalized)
        );
    }

    private void drawPointLabels(
            Graphics2D graphics,
            TemperaturePoint point,
            int x,
            int y,
            int height) {

        final Font originalFont =
                graphics.getFont();

        graphics.setFont(
                originalFont.deriveFont(
                        Font.BOLD,
                        TEMPERATURE_FONT_SIZE
                )
        );

        final String temperatureText =
                String.format(
                        "%.0f°C",
                        point.getTemperature()
                );

        graphics.drawString(
                temperatureText,
                x - 12,
                y - 8
        );

        graphics.setFont(
                originalFont.deriveFont(
                        Font.PLAIN,
                        TIME_FONT_SIZE
                )
        );

        final String timeText =
                point.getTime().format(
                        TIME_FORMATTER
                );

        graphics.drawString(
                timeText,
                x - 14,
                height - 15
        );
    }

    private double findMinimumTemperature() {

        double minimum =
                temperaturePoints
                        .get(0)
                        .getTemperature();

        for (TemperaturePoint point
                : temperaturePoints) {

            minimum = Math.min(
                    minimum,
                    point.getTemperature()
            );
        }

        return minimum;
    }

    private double findMaximumTemperature() {
        double maximum =
                temperaturePoints
                        .get(0)
                        .getTemperature();
        for (TemperaturePoint point
                : temperaturePoints) {
            maximum = Math.max(
                    maximum,
                    point.getTemperature()
            );
        }

        return maximum;
    }
}