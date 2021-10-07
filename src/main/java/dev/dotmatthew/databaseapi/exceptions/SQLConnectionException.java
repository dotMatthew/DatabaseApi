package dev.dotmatthew.databaseapi.exceptions;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class SQLConnectionException extends RuntimeException {

    public SQLConnectionException() { super("An error occurred while connecting to a SQL database"); }

    public SQLConnectionException(String message) {
        super(message);
    }

    public SQLConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}

