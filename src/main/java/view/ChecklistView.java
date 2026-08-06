package view;

import java.awt.Color;
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
//import interface_adapter.checklist.ToggleTaskController

public class ChecklistView extends JPanel implements PropertyChangeListener{
    private final ChecklistViewModel viewModel;

    private final JPanel taskPanel = new JPanel();
    private final JLabel emptyLabel = new JLabel("No tasks yet.", SwingConstants.CENTER);

    private final Map<String, JCheckBox> checkBoxesByTaskId =
            new HashMap<>();

    public ChecklistView(
            ChecklistViewModel viewModel
    ) {
        this.viewModel = viewModel;

        viewModel.addPropertyChangeListener(this);

        setLayout(new java.awt.BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Checklist"));

        taskPanel.setLayout(new GridLayout(0, 1, 4, 4));
        add(taskPanel, java.awt.BorderLayout.NORTH);

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
//        else {
//            for (TaskState task : state.getTasks()) {
//                taskPanel.add(createTaskRow(task));
//            }
//        }

        revalidate();
        repaint();
    }

}
