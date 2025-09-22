package labussy.task;

import labussy.time.Dates;
/**
 * Task that spans a time period (from start to end).
 */
public class Event extends Task {

    protected Dates from;
    protected Dates to;

    /**
     * Constructs a new Event.
     * @param description parameter
     * @param from parameter
     * @param to parameter
     */

    public Event(String description, Dates from, Dates to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the primary date associated with this task.
     * @return result
     */

    public Dates getDate() { return from; }

    /**
     * Returns true if this item occurs within the next day.
     * @return result
     */

    public boolean dueSoon() {
        return (from.daysUntil() < 1);
    }

    /**
     * Returns a human-readable representation for lists and storage echoes.
     * @return result
     */

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to:" + to + ")";
    }
}