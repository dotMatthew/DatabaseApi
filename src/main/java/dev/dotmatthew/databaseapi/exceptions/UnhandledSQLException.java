package dev.dotmatthew.databaseapi.exceptions;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class UnhandledSQLException extends RuntimeException {

    public UnhandledSQLException() { super(); }

    public UnhandledSQLException(String message) {
        super(message);
    }

    public UnhandledSQLException(String message, Throwable cause) {
        super(message, cause);
    }


}

