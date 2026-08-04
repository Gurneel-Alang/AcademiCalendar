package entity.reminder;

import data_access.ReminderScheduler;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReminderChoicesTest {

    @Test
    public void checkCorrectOffset() {
        ReminderChoices oneHour = ReminderChoices.oneHour();

        assertEquals("In 1 hour", oneHour.getLabel());
        assertEquals("1 hour ago", oneHour.getPastLabel());
        assertEquals(3600, oneHour.getOffsetTime());
        assertEquals("In 1 hour", oneHour.toString());
    }

    @Test
    public void returnOptionsInOrder() {
        ReminderChoices[] options = ReminderChoices.all();

        assertEquals(3, options.length);
        assertEquals("In 1 hour", options[0].getLabel());
        assertEquals("In 3 hours", options[1].getLabel());
        assertEquals("In 7 days", options[2].getLabel());

        assertEquals(3600, options[0].getOffsetTime());
        assertEquals(10800, options[1].getOffsetTime());
        assertEquals(604800, options[2].getOffsetTime());
    }
}
