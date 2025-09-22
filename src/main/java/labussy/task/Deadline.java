package labussy.task;

import labussy.time.Dates;

/**
 * Task that must be completed by a specific date/time.
 */

public class Deadline extends Task {

    protected Dates by;

    /**
     * Constructs a new Deadline.
     * @param description parameter
     * @param by parameter
     */

    public Deadline(String description, Dates by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the primary date associated with this task.
     * @return result
     */

    public Dates getDate() { return by; }

    /**
     * Returns true if this item occurs within the next day.
     * @return result
     */

    public boolean dueSoon() {
        return (by.daysUntil() < 1);
    }

    /**
     * Returns a human-readable representation for lists and storage echoes.
     * @return result
     */

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}