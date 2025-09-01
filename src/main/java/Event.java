public class Event extends Task {

    protected Dates from;
    protected Dates to;

    public Event(String description, Dates from, Dates to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to:" + to + ")";
    }
}