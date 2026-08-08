package entity.event;

import java.time.LocalDate;

/**
 * The representation of an event.
 */
public interface EventInterface {

    /**
     * Return the ID of this event.
     * @return the ID of this event
     */
    int getId();

    /**
     * Set the ID of this event.
     * @param id the ID to set
     */
    void setId(int id);

    /**
     * Return the title of this event.
     * @return the title of this event
     */
    String getTitle();

    /**
     * Return the description of this event.
     * @return the description of this event
     */
    String getDescription();

    /**
     * Return the start date of this event.
     * @return the start date of this event
     */
    LocalDate getStartDate();

    /**
     * Return the end date of this event.
     * @return the end date of this event
     */
    LocalDate getEndDate();

    /**
     * Return if this event occurs on a given date.
     * @param date the date
     * @return if this event occurs on the given date
     */
    boolean occursOn(LocalDate date);
}
