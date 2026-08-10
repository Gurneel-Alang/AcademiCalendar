package use_case.task.load_checklist;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import entity.task.Task;

public class DueDateSortStrategyTest {

    @Test
    public void sortsAscendingByDueDate() {
        final Task later = new Task("id-1", "Later", LocalDate.of(2026, 9, 1), false);
        final Task earlier = new Task("id-2", "Earlier", LocalDate.of(2026, 8, 1), false);

        final DueDateSortStrategy strategy = new DueDateSortStrategy();
        final List<Task> sorted = strategy.sort(Arrays.asList(later, earlier));

        assertEquals("id-2", sorted.get(0).getId());
        assertEquals("id-1", sorted.get(1).getId());
    }

    @Test
    public void nullDueDatePlacedLast() {
        final Task withDate = new Task("id-1", "Has date", LocalDate.of(2026, 8, 1), false);
        final Task noDate = new Task("id-2", "No date", null, false);

        final DueDateSortStrategy strategy = new DueDateSortStrategy();
        final List<Task> sorted = strategy.sort(Arrays.asList(noDate, withDate));

        assertEquals("id-1", sorted.get(0).getId());
        assertEquals("id-2", sorted.get(1).getId());
    }

    @Test
    public void emptyListReturnsEmpty() {
        final DueDateSortStrategy strategy = new DueDateSortStrategy();
        final List<Task> sorted = strategy.sort(Collections.emptyList());

        assertTrue(sorted.isEmpty());
    }
}
