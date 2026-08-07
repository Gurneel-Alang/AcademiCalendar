package entity.event;

import java.time.LocalDate;

/**
 * The representation of a factory for creating events.
 */
public interface EventFactoryInterface {

    /**
     * Create a new Event.
     * @param title the title of the event
     * @param description the description of the event
     * @param startDate the start date of the event
     * @param endDate the end date of the event
     */
    EventInterface create(String title, String description, LocalDate startDate, LocalDate endDate);
}
