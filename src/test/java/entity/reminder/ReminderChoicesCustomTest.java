package entity.reminder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ReminderChoicesCustomTest {

    /**
     * This test checks whether the custom hours will produce correct labels and offsets.
     */
    @Test
    public void customHoursTest() {
        ReminderChoices three = ReminderChoices.customHours(3);

        assertEquals("In 3 hour(s)", three.getLabel());
        assertEquals("3 hour(s) ago", three.getPastLabel());
        assertEquals(10800, three.getOffsetTime());
    }

}
