package view;

import interface_adapter.checklist.TaskState;

public class CompletedFormatterDecorator implements TaskDisplayFormatter {
    private final TaskDisplayFormatter wrapped;

    public CompletedFormatterDecorator(TaskDisplayFormatter wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String format(TaskState task) {
        final String inner = wrapped.format(task);

        if (task.isCompleted()) {
            return "<font color='gray'><strike>" + inner + "</strike></font>";
        }
        return inner;
    }
}