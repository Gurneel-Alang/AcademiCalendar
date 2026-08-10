package view.checklist_view;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interface_adapter.checklist.ChecklistState;
import interface_adapter.checklist.ChecklistViewModel;
import interface_adapter.checklist.TaskState;
import interface_adapter.checklist.toggle_task.ToggleTaskController;

public class ChecklistView extends JPanel
        implements PropertyChangeListener {

    private final ChecklistViewModel viewModel;
    private final ToggleTaskController toggleTaskController;

    private final JPanel taskPanel = new JPanel();
    private final JLabel emptyLabel =
            new JLabel("No tasks yet.", SwingConstants.CENTER);

    private final JLabel progressLabel = new JLabel("", SwingConstants.CENTER);

    private final Map<String, JCheckBox> checkBoxesByTaskId =
            new HashMap<>();

    public ChecklistView(
            ChecklistViewModel viewModel,
            ToggleTaskController toggleTaskController
    ) {
        this.viewModel = viewModel;
        this.toggleTaskController = toggleTaskController;

        viewModel.addPropertyChangeListener(this);

        setLayout(new java.awt.BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Checklist"));

        taskPanel.setLayout(new GridLayout(0, 1, 4, 4));
        add(taskPanel, java.awt.BorderLayout.NORTH);

        progressLabel.setFont(progressLabel.getFont().deriveFont(Font.ITALIC));
        add(progressLabel, java.awt.BorderLayout.SOUTH);

        render(viewModel.getState());
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        render(viewModel.getState());
    }

    private void render(ChecklistState state) {
        taskPanel.removeAll();
        checkBoxesByTaskId.clear();

        if (state.getTasks().isEmpty()) {
            taskPanel.add(emptyLabel);
        }
        else {
            for (TaskState task : state.getTasks()) {
                taskPanel.add(createTaskRow(task));
            }
        }

        progressLabel.setText(state.getProgressSummary());

        revalidate();
        repaint();
    }

    private Component createTaskRow(TaskState task) {
        final TaskDisplayFormatter formatter = new CompletedFormatterDecorator(
                new OverdueFormatterDecorator(
                        new BaseTaskFormatter()
                )
        );
        final JCheckBox checkBox =
                new JCheckBox("<html>" + formatter.format(task) + "</html>");

        checkBox.setSelected(task.isCompleted());

        /*
         * Store the task ID separately. Do not use the description as an ID
         * because two tasks may have the same description.
         */
        checkBoxesByTaskId.put(task.getId(), checkBox);

        checkBox.addActionListener(event ->
                toggleTaskController.execute(task.getId())
        );

        return checkBox;
    }
}
