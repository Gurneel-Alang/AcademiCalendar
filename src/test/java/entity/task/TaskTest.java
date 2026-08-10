package entity.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void constructorSetsFieldsCorrectly(){
        final LocalDate dueDate = LocalDate.of(2026, 8, 10);
        final Task task = new Task("id-1", "Study week 3", dueDate, false);

        assertEquals("id-1", task.getId());
        assertEquals("Study week 3", task.getDescription());
        assertEquals(dueDate, task.getDueDate());
        assertFalse(task.isCompleted());
    }

    @Test
    public void dueDateCanBeNull(){
        final Task task = new Task("id-1", "No due date", null, false);
        assertNull(task.getDueDate());
    }

    @Test
    public void blankIdThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Task(null, "desc", null, false));
    }

    @Test
    public void blankDescThrowsException(){
        assertThrows(IllegalArgumentException.class, () -> new Task("id-1", "",null, false));
    }

    @Test
    public void toggleCompletedFlipsState(){
        final Task task = new Task("id-1", "flip", null, false);
        task.toggleCompleted();
        assertTrue(task.isCompleted());

        task.toggleCompleted();
        assertFalse(task.isCompleted());

    }
}
