package view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import interface_adapter.checklist.TaskState;
import view.checklist_view.BaseTaskFormatter;

public class BaseTaskFormatterTest {

    @Test
    public void includesDueDateWhenPresent() {
        final TaskState task = new TaskState("id-1", "Study", LocalDate.of(2026, 8, 20), false);
        final BaseTaskFormatter formatter = new BaseTaskFormatter();

        final String result = formatter.format(task);

        assertTrue(result.contains("Study"));
        assertTrue(result.contains("2026-08-20"));
    }

    @Test
    public void omitsDueDateWhenNull() {
        final TaskState task = new TaskState("id-1", "Study", null, false);
        final BaseTaskFormatter formatter = new BaseTaskFormatter();

        assertEquals("Study", formatter.format(task));
    }

    @Test
    public void escapesHtmlCharacters() {
        final TaskState task = new TaskState("id-1", "A & B < C", null, false);
        final BaseTaskFormatter formatter = new BaseTaskFormatter();

        final String result = formatter.format(task);

        assertTrue(result.contains("&amp;"));
        assertTrue(result.contains("&lt;"));
    }
}
