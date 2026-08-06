package entity.reminder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReminderChoicesTest {

    // Run this file to run these tests

    /**
     * This test returns the offset time for one hour, ensuring ReminderScheduler is working correctly
     */
    @Test
    public void checkCorrectOffset() {
        ReminderChoices oneHour = ReminderChoices.oneHour();

        assertEquals("In 1 hour", oneHour.getLabel());
        assertEquals("1 hour ago", oneHour.getPastLabel());
        assertEquals(3600, oneHour.getOffsetTime());
        assertEquals("In 1 hour", oneHour.toString());
    }

    /**
     * This test should return all the time options, in order.
     */
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

    /**
     * This test check for whitespace leading/trailing labels.
     */
    @Test
    public void noWhitespace(){
        for (ReminderChoices choice : ReminderChoices.all()){
            assertEquals(choice.getLabel(), choice.getLabel().trim());
            assertEquals(choice.getPastLabel(), choice.getPastLabel().trim());
        }
    }

    /**
     * This test ensures offset are never negative.
     */
    @Test
    public void checkNegative(){
        for (ReminderChoices choice : ReminderChoices.all()){
            assertTrue(choice.getLabel() + " is negative offset",
                    choice.getOffsetTime() >= 0);
        }
    }

}
