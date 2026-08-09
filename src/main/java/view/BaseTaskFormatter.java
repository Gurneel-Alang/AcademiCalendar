package view;

import interface_adapter.checklist.TaskState;

public class BaseTaskFormatter implements TaskDisplayFormatter {

    @Override
    public String format(TaskState task) {
        final String description = escapeHtml(task.getDescription());

        if (task.getDueDate() != null) {
            return description + " (due " + task.getDueDate() + ")";
        }
        return description;

    }

    private String escapeHtml(String text) {
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
