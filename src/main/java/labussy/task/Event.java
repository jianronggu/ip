package labussy.task;

import labussy.time.Dates;

public class Event extends Task {

    protected Dates from;
    protected Dates to;

    public Event(String description, Dates from, Dates to) {
        super(description);
        this.from = from;
        this.to = to;
    }
    public Dates getDate() { return from; }

    public boolean dueSoon() {
        return (from.daysUntil() < 1);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to:" + to + ")";
    }
}