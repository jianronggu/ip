package labussy.task;

import labussy.time.Dates;

public class Deadline extends Task {

    protected Dates by;

    public Deadline(String description, Dates by) {
        super(description);
        this.by = by;
    }

    // return date of the task
    public Dates getDate() { return by; }

    // return true/false the task is due within a day
    public boolean dueSoon() {
        return (by.daysUntil() < 1);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}