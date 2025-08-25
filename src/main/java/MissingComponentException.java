public class MissingComponentException extends Exception {
    public MissingComponentException() {
        super("The date/time is missing!!!!! e.g. deadline submit homework /by Sunday, " +
                "event sports day /from Monday 1pm /to 3pm");
    }
}
