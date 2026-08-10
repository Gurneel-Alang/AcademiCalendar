package view.checklist_view;

import java.time.LocalDate;

import interface_adapter.checklist.TaskState;

public class OverdueFormatterDecorator implements TaskDisplayFormatter {
    private final TaskDisplayFormatter wrapped;

    public OverdueFormatterDecorator(TaskDisplayFormatter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String format(TaskState task) {
        final String inner = wrapped.format(task);

        final boolean isOverdue = task.getDueDate() != null
                && task.getDueDate().isBefore(LocalDate.now())
                && !task.isCompleted();

        if (isOverdue) {
            return "<font color='red'>" + inner + "</font>";
        }
        return inner;
    }
}
