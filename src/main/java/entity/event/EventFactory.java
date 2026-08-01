package entity.event;

import java.time.LocalDate;

/**
 * An implementation of the EventFactoryInterface interface.
 */
public class EventFactory implements EventFactoryInterface {

    @Override
    public EventInterface create(String title, String description, LocalDate startDate, LocalDate endDate) {
        return new Event(title, description, startDate, endDate);
    }
}
