package dev.dotmatthew.databaseapi.exceptions;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class SQLQueryException extends RuntimeException {

    public SQLQueryException() { super(); }

    public SQLQueryException(String message) {
        super(message);
    }

    public SQLQueryException(String message, Throwable cause) {
        super(message, cause);
    }

}

