package view;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import interface_adapter.checklist.TaskState;

public class CompletedFormatterDecoratorTest {

    @Test
    public void marksCompletedTasksWithStrikethrough() {
        final TaskState task = new TaskState("id-1", "Done", null, true);
        final TaskDisplayFormatter formatter = new CompletedFormatterDecorator(new BaseTaskFormatter());

        final String result = formatter.format(task);

        assertTrue(result.contains("<strike>"));
        assertTrue(result.contains("color='gray'"));
    }

    @Test
    public void doesNotModifyIncompleteTasks() {
        final TaskState task = new TaskState("id-1", "Not done", null, false);
        final TaskDisplayFormatter formatter = new CompletedFormatterDecorator(new BaseTaskFormatter());

        final String result = formatter.format(task);

        assertFalse(result.contains("<strike>"));
    }

    @Test
    public void decoratorsComposeCorrectly() {
        final TaskState task = new TaskState("id-1", "Done", null, true);
        final TaskDisplayFormatter formatter =
                new CompletedFormatterDecorator(new OverdueFormatterDecorator(new BaseTaskFormatter()));

        final String result = formatter.format(task);

        assertTrue(result.contains("Done"));
        assertTrue(result.contains("<strike>"));
    }
}
