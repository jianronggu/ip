package labussy.task;

import labussy.time.Dates;

public class Deadline extends Task {

    protected Dates by;

    public Deadline(String description, Dates by) {
        super(description);
        this.by = by;
    }

    public Dates getDate() { return by; }

    public boolean dueSoon() {
        return (by.daysUntil() < 1);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}