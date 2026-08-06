package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.create_task.CreateTaskController;
import view.ChecklistView;


public class EventDetailsView extends JPanel {

    private final CreateTaskController createTaskController;

    private final JLabel eventTitleLabel = new JLabel();
    private final JLabel eventDateLabel = new JLabel();

    private final JTextField taskInput = new JTextField(25);
    private final JButton addTaskButton = new JButton("Add Task");

    private String currentEventId;

    public EventDetailsView(
            CreateTaskController createTaskController,
            ChecklistViewModel checklistViewModel
    ) {
        this.createTaskController = createTaskController;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        final JPanel informationPanel = new JPanel();
        informationPanel.setLayout(
                new javax.swing.BoxLayout(
                        informationPanel,
                        javax.swing.BoxLayout.Y_AXIS
                )
        );

        informationPanel.add(eventTitleLabel);
        informationPanel.add(eventDateLabel);

        final ChecklistView checklistView =
                new ChecklistView(
                        checklistViewModel
                );

        final JPanel addTaskPanel = new JPanel(new FlowLayout());
        addTaskPanel.add(taskInput);
        addTaskPanel.add(addTaskButton);

        add(informationPanel, BorderLayout.NORTH);
        add(checklistView, BorderLayout.CENTER);
        add(addTaskPanel, BorderLayout.SOUTH);

        addTaskButton.addActionListener(event -> addTask());
        taskInput.addActionListener(event -> addTask());

        checklistViewModel.addPropertyChangeListener(event -> {
            final String error =
                    checklistViewModel.getState().getErrorMessage();

            if (error != null && !error.isBlank()) {
                JOptionPane.showMessageDialog(
                        this,
                        error,
                        "Checklist Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });
    }

    /**
     * Changes this view to display a particular event.
     */
    public void displayEvent(
            String eventId,
            String eventTitle,
            String eventDate
    ) {
        currentEventId = eventId;
        eventTitleLabel.setText("Event: " + eventTitle);
        eventDateLabel.setText("Date: " + eventDate);

    }

    private void addTask() {
        if (currentEventId == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Select an event before adding a task."
            );
            return;
        }

        final String description = taskInput.getText();
        createTaskController.execute(currentEventId, description);

        if (!description.isBlank()) {
            taskInput.setText("");
        }
    }
}