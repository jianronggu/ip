package labussy.exception;

// For deadline and event, if date is missing throw this error.

public class MissingComponentException extends Exception {
    public MissingComponentException() {
        super("The date/time is missing!!!!!");
    }
}
