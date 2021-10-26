package dev.dotmatthew.databaseapi.statement;

import dev.dotmatthew.databaseapi.exceptions.MessageException;
import dev.dotmatthew.databaseapi.exceptions.SQLQueryException;
import dev.dotmatthew.databaseapi.exceptions.UnhandledSQLException;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class Statement {

    private final PreparedStatement statement;

    private ResultSet set;

    /**
     *
     * Creates a prepared statement.
     *
     * @param statement {@link PreparedStatement}
     */
    public Statement(final PreparedStatement statement) {
        this.statement = statement;
    }

    /**
     *
     * Set the arguments for the current statement.
     *
     * Set the arguments like:
     *
     * preparedStatement.setString(1, "Test");
     * @param arguments an {@link Array} of arguments
     */
    private void setArguments(final Object... arguments) {
        if(arguments == null) return;
        for(int i = 0; i < arguments.length; i++) {
            int index = i + 1;
            try {
                this.statement.setObject(index, arguments[i]);
            } catch (final Exception ex) {
                ex.printStackTrace();
            }

        }
    }

    /**
     *
     * Execute query and safe it inside a result set.
     *
     * @param arguments an {@link Array} of arguments
     * @return instance of current statement
     */
    public Statement executeQuery(Object... arguments) {
        setArguments(arguments);
        try {
            set = this.statement.executeQuery();
            return this;
        } catch (final Exception ex) {
            throw new UnhandledSQLException("Unable to get ResultSet.", ex);
        }
    }

    /**
     *
     * Execute a query without a result.
     *
     * @param arguments an {@link Array} of arguments
     */
    public void execute(Object... arguments) {
        setArguments(arguments);
        try {
            this.statement.execute();
        } catch (final SQLException ex) {
            throw new SQLQueryException("Unable to execute query.", ex);
        }

    }

    @Deprecated
    public int executeUpdate(Object... arguments) {
        setArguments(arguments);
        try {
            return this.statement.executeUpdate();
        } catch (final Exception ex) {
            throw new SQLQueryException("Unable to execute update query.", ex);
        }
    }

    @Deprecated
    public Long executeLargeUpdate(Object... arguments) {
        setArguments(arguments);
        try {
            return this.statement.executeLargeUpdate();
        } catch (final Exception ex) {
            throw new SQLQueryException("Unable to execute Large Update.", ex);
        }

    }

    /**
     * Return the result set from {@link Statement#executeQuery}.
     * @return {@link ResultSet}
     */
    public ResultSet getSet() {
        return this.set;
    }

    /**
     *
     * Get a list from the ResultSet without own exception handling.
     *
     * @param filter column name of the result
     * @param clazz class of result. like {@link Integer#getClass()}
     * @param <T> type of the result
     * @return new {@link List}
     */
    public <T> List<T> getList(final String filter, Class<T> clazz) {
        if(filter == null) return new ArrayList<>();
        final List<T> list = new ArrayList<>();
        try {
            while(this.set.next()) list.add(clazz.cast(this.set.getObject(filter)));
            this.set.close();
        } catch(final Exception ex) {
            throw new MessageException("An error occurred.", ex);
        }
        return list;
    }

    /**
     *
     * Get result from ResultSet without own exception handling.
     *
     * @param filter column name of the result
     * @param clazz class of result. like {@link Integer#getClass()}
     * @param <T> the type of the result
     * @return new {@link T}
     */
    public <T> T get(final String filter, final Class<T> clazz) {
        if(filter == null) return null;
        try {
            while(this.set.next()) return clazz.cast(this.set.getObject(filter));
            this.set.close();
            return null;
        } catch(final Exception ex) {
            throw new MessageException("An error occurred.", ex);
        }
    }

}