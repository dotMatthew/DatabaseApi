package net.mdollenbacher.databaseapi.exceptions;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class FutureIsEmptyException extends RuntimeException {

    public FutureIsEmptyException() { super("The future <?> Element must not be empty!"); }

    public FutureIsEmptyException(String message) {
        super(message);
    }

    public FutureIsEmptyException(String message, Throwable cause) {
        super(message, cause);
    }

}

