package entity.event;

import java.util.Date;

/**
 * An implementation of the EventInterface interface.
 */
public class Event implements EventInterface{

    private static int nextId = 1;
    private int id;
    private String title;
    private String description;
    private Date startDate;
    private Date endDate;

    public Event(String title, String description,
                 Date startDate, Date endDate) {
        this.id = nextId;
        nextId++;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public int getId() { return id; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public String getDescription() { return description; }

    @Override
    public Date getStartDate() { return startDate; }

    @Override
    public Date getEndDate() { return endDate; }
}
