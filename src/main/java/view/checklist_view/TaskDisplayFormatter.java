package view.checklist_view;

import interface_adapter.checklist.TaskState;

public interface TaskDisplayFormatter {
    String format(TaskState task);
}
