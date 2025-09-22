package labussy.exception;

//Identifier if blank, throw this error.

public class IdentifierException extends Exception {
    public IdentifierException() {
        super("The identifier cannot be empty!!!!! e.g. todo write a book");
    }
}
