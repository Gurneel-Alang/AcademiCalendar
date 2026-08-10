package view;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import interface_adapter.checklist.TaskState;

public class OverdueFormatterDecoratorTest {

    @Test
    public void marksRedWhenOverdueAndIncomplete() {
        final TaskState task = new TaskState("id-1", "Late task", LocalDate.now().minusDays(1), false);
        final TaskDisplayFormatter formatter = new OverdueFormatterDecorator(new BaseTaskFormatter());

        final String result = formatter.format(task);

        assertTrue(result.contains("color='red'"));
    }

    @Test
    public void doesNotMarkRedWhenDueDateIsFuture() {
        final TaskState task = new TaskState("id-1", "Future task", LocalDate.now().plusDays(1), false);
        final TaskDisplayFormatter formatter = new OverdueFormatterDecorator(new BaseTaskFormatter());

        final String result = formatter.format(task);

        assertFalse(result.contains("color='red'"));
    }

    @Test
    public void doesNotMarkRedWhenCompletedEvenIfOverdue() {
        final TaskState task = new TaskState("id-1", "Done late", LocalDate.now().minusDays(1), true);
        final TaskDisplayFormatter formatter = new OverdueFormatterDecorator(new BaseTaskFormatter());

        final String result = formatter.format(task);

        assertFalse(result.contains("color='red'"));
    }

    @Test
    public void doesNotMarkRedWhenDueDateIsNull() {
        final TaskState task = new TaskState("id-1", "No date", null, false);
        final TaskDisplayFormatter formatter = new OverdueFormatterDecorator(new BaseTaskFormatter());

        final String result = formatter.format(task);

        assertFalse(result.contains("color='red'"));
    }
}
