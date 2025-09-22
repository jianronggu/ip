package labussy.exception;

//Task instruction if not specified, throw this error.

public class BlankException extends Exception {
    public BlankException() {
        super("The task must be specified!!!!! e.g. todo xxx, deadline xxx, event xxx");
    }
}
