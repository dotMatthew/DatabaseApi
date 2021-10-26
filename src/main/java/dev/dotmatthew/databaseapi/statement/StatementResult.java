package dev.dotmatthew.databaseapi.statement;

import java.util.concurrent.Future;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class StatementResult {

    private final Future<?> future;

    public <T> StatementResult(Future<T> future) {
        this.future = future;
    }

    public <T> T get(final Class<T> clazz) {
        try {
            return clazz.cast(future.get());
        } catch (final Exception ex) {
            throw new RuntimeException("", ex);
        }
    }


}
