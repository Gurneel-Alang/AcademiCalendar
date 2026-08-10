package entity.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CommonTaskFactoryTest {

    @Test
    public void createGeneratesUniqueIds() {
        final TaskFactory factory = new CommonTaskFactory();

        final Task first = factory.create("Task A", null);
        final Task second = factory.create("Task B", null);

        assertNotNull(first.getId());
        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    public void createStartsIncomplete() {
        final TaskFactory factory = new CommonTaskFactory();
        final Task task = factory.create("Task A", null);

        assertFalse(task.isCompleted());
    }

    @Test
    public void createPreservesDescriptionAndDueDate() {
        final TaskFactory factory = new CommonTaskFactory();
        final LocalDate dueDate = LocalDate.of(2026, 9, 1);

        final Task task = factory.create("Study week 3", dueDate);

        assertEquals("Study week 3", task.getDescription());
        assertEquals(dueDate, task.getDueDate());
    }
}