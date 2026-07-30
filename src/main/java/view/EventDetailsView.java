package view;

import interface_adapter.create_task.ChecklistState;
import interface_adapter.create_task.ChecklistViewModel;
import interface_adapter.create_task.CreateTaskController;
import interface_adapter.create_task.TaskState;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Displays information for one Event and its checklist.
 */
public class EventDetailsView extends JPanel
        implements PropertyChangeListener {

    private final String eventId;

    private final CreateTaskController createTaskController;
    private final ChecklistViewModel checklistViewModel;

    private final JTextField taskDescriptionField =
            new JTextField();

    private final JButton addTaskButton =
            new JButton("Add Task");

    private final JPanel checklistPanel =
            new JPanel();

    public EventDetailsView(
            String eventId,
            String eventName,
            CreateTaskController createTaskController,
            ChecklistViewModel checklistViewModel
    ) {
        this.eventId = eventId;
        this.createTaskController = createTaskController;
        this.checklistViewModel = checklistViewModel;

        checklistViewModel.addPropertyChangeListener(this);

        configureLayout(eventName);
        configureActions();
    }

    private void configureLayout(String eventName) {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(
                15,
                15,
                15,
                15
        ));

        final JLabel eventTitle =
                new JLabel(eventName);

        add(eventTitle, BorderLayout.NORTH);

        checklistPanel.setLayout(
                new BoxLayout(
                        checklistPanel,
                        BoxLayout.Y_AXIS
                )
        );

        final JScrollPane checklistScrollPane =
                new JScrollPane(checklistPanel);

        checklistScrollPane.setPreferredSize(
                new Dimension(400, 250)
        );

        add(checklistScrollPane, BorderLayout.CENTER);

        final JPanel addTaskPanel =
                new JPanel(new BorderLayout(5, 5));

        addTaskPanel.add(
                taskDescriptionField,
                BorderLayout.CENTER
        );

        addTaskPanel.add(
                addTaskButton,
                BorderLayout.EAST
        );

        add(addTaskPanel, BorderLayout.SOUTH);
    }

    private void configureActions() {
        addTaskButton.addActionListener(
                this::handleAddTask
        );

        taskDescriptionField.addActionListener(
                this::handleAddTask
        );
    }

    private void handleAddTask(ActionEvent event) {
        final String description =
                taskDescriptionField.getText();

        createTaskController.execute(
                eventId,
                description
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (ChecklistViewModel.STATE_PROPERTY.equals(
                event.getPropertyName()
        )) {
            renderChecklist();
        }
    }

    private void renderChecklist() {
        final ChecklistState state =
                checklistViewModel.getState();

        if (state.getErrorMessage() != null) {
            JOptionPane.showMessageDialog(
                    this,
                    state.getErrorMessage(),
                    "Could Not Add Task",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        /*
         * Prevent duplicate Swing components when the
         * complete state is rendered again.
         */
        checklistPanel.removeAll();

        for (TaskState taskState : state.getTasks()) {
            final JCheckBox taskCheckBox =
                    new JCheckBox(
                            taskState.getDescription()
                    );

            taskCheckBox.setSelected(
                    taskState.isCompleted()
            );

            /*
             * This currently changes only the local UI state.
             * A separate CompleteTask use case should eventually
             * handle checkbox changes.
             */
            taskCheckBox.addActionListener(actionEvent ->
                    taskState.setCompleted(
                            taskCheckBox.isSelected()
                    )
            );

            checklistPanel.add(taskCheckBox);
        }

        taskDescriptionField.setText("");

        checklistPanel.revalidate();
        checklistPanel.repaint();
    }
}