package view;

import org.junit.Test;
import view.study_timer_view.StudyTimerView;

import static org.junit.Assert.assertEquals;

public class StudyTimerViewTest {

    /**
     * This test checks whether the timing format is displaying correctly.
     */
    @Test
    public void checkFormat() {
        assertEquals("00:00:42", StudyTimerView.formatTime(42));
        assertEquals("00:01:00", StudyTimerView.formatTime(60));
        assertEquals("00:02:04", StudyTimerView.formatTime(124));
        assertEquals("02:02:04", StudyTimerView.formatTime(7324));
    }
}
