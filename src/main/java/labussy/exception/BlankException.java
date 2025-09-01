package labussy.exception;

public class BlankException extends Exception {
    public BlankException() {
        super("The task must be specified!!!!! e.g. todo xxx, deadline xxx, event xxx");
    }
}
