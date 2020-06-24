package net.mdollenbacher.databaseapi.exceptions;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class MessageException extends RuntimeException{

    public MessageException() {}

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

}

