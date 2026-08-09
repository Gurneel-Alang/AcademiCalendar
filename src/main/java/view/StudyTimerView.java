package view;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

// Study timer that allows users to account how much time they have spent studying,
// and additional motivation messages.

public class StudyTimerView extends JPanel {
    private static final int ONE_SECOND_IN_MS = 1000;
    private static final int THIRTY_SECONDS_IN_MS = 30000;
    private static final float FONT_SIZE = 50f;
    private static final float MESSAGE_FONT_SIZE = 15f;

    private static final Color DARK_BLUE = new Color(25, 42, 86);
    private static final Color SOFT_BLUE_GREY = new Color(90, 105, 140);
    private static final Color BACKGROUND = new Color(244, 247, 252);

    private static final String[] MESSAGES = {
            "Win the timer, win the day.",
            "\"It always seems impossible until it's done.\" - Nelson Mandela",
            "Don't stop until you're proud.",
            "One page, one problem, one minute at a time.",
            "Progress, not perfection.",
            "Consistency beats intensity."
    };

    private final Random random = new Random();
    private int lastMessageIndex = -1;
    private int secondsElapsed;
    private final JLabel timeLabel = new JLabel("00:00:00");
    private final JLabel messageLabel = new JLabel();
    private final Timer timer;
    private final Timer messageTimer;

    public StudyTimerView(){
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        final JLabel title = new JLabel("Study Timer", SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        title.setFont(title.getFont().deriveFont(Font.BOLD, 30f));
        add(title, BorderLayout.NORTH);

        final JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeLabel.setFont(new Font("SansSerif", Font.BOLD, (int) FONT_SIZE));
        timeLabel.setForeground(DARK_BLUE);

        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        messageLabel.setFont(new Font("SansSerif", Font.ITALIC, (int) MESSAGE_FONT_SIZE));
        messageLabel.setForeground(SOFT_BLUE_GREY);
        messageLabel.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));

        centerPanel.add(timeLabel);
        centerPanel.add(messageLabel);
        add(centerPanel, BorderLayout.CENTER);

        final JButton startButton = new JButton("Start");
        final JButton pauseButton = new JButton("Pause");
        final JButton resetButton = new JButton("Reset");
        final JPanel buttons = new JPanel();
        buttons.add(startButton);
        buttons.add(pauseButton);
        buttons.add(resetButton);
        add(buttons, BorderLayout.SOUTH);

        timer = new Timer(ONE_SECOND_IN_MS, event -> {
            secondsElapsed++;
            updateLabel();
        });

        messageTimer = new Timer(THIRTY_SECONDS_IN_MS, event -> showRandomMessage());
        showRandomMessage();
        messageTimer.start();

        startButton.addActionListener(event -> {
            if (!timer.isRunning()) {
                timer.start();
            }
        });
        pauseButton.addActionListener(event -> timer.stop());
        resetButton.addActionListener(event -> {
            timer.stop();
            secondsElapsed = 0;
            updateLabel();
        });
    }

    static String formatTime(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void updateLabel() {
        timeLabel.setText(formatTime(secondsElapsed));
    }

    private void showRandomMessage() {
        int index = random.nextInt(MESSAGES.length);
        while (index == lastMessageIndex) {
            index = random.nextInt(MESSAGES.length);
        }
        lastMessageIndex = index;
        messageLabel.setText(MESSAGES[index]);
    }
}
