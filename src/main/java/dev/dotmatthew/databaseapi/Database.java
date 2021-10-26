package dev.dotmatthew.databaseapi;

import dev.dotmatthew.databaseapi.exceptions.UnhandledSQLException;
import dev.dotmatthew.databaseapi.tablebuilder.TableBuilder;
import dev.dotmatthew.databaseapi.exceptions.SQLConnectionException;
import dev.dotmatthew.databaseapi.statement.Statement;
import lombok.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.*;

@Data
public class Database {

    private int threads = 0;

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;

    private String databaseFile;

    private final Connection connection;
    private final DatabaseType databaseType;

    private final ExecutorService executorPool;
    final ThreadFactory threadFactory = r -> {
        final Thread thread = new Thread(r, "threading_pool.thread_" + (++threads));
        thread.setDaemon(true);
        return thread;
    };

    /**
     *
     * Creates a new database instance.
     *
     * @param databaseType type of database / driver
     * @param host sql host
     * @param port database port
     * @param database database name
     * @param username database username
     * @param password database password
     */
    public Database(final DatabaseType databaseType, final String host, final int port, final String database, final String username, final String password) {

        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        this.databaseType = databaseType;

        this.executorPool = Executors.newCachedThreadPool(this.threadFactory);

        this.connection = getConnection();

    }

    /**
     *
     * Creates a new database instance.
     *
     * @param databaseFile location of the database
     */
    public Database(final String databaseFile) {

        this.databaseFile = databaseFile;
        this.databaseType = DatabaseType.SQLite;

        this.executorPool = Executors.newCachedThreadPool(this.threadFactory);

        this.connection = getConnection();

    }

    /**
     *
     * Returns a new TableBuilder instance for this database.
     *
     * @param name database name
     * @return new TableBuilder instance
     */
    public TableBuilder getTableBuilder(final String name) {
        return new TableBuilder(this, name);
    }

    /**
     *
     * Returns a new connection for this database.
     *
     * @return new database connection
     */
    public Connection getConnection() {
        if(connection != null) {
            return this.connection;
        }
        if(databaseType.equals(DatabaseType.MySQL)) {
            try {
                return DriverManager.getConnection("jdbc:mysql://"+ host + ":" + port + "/" + database + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Berlin", username, password);
            } catch (final SQLException ex) {
                throw new SQLConnectionException("There was an error connecting. Possibly incorrect syntax?", ex);
            }
        } else {
            try {
                return DriverManager.getConnection("jdbc:sqlite:"+databaseFile);
            } catch(final SQLException e) {
                throw new SQLConnectionException("There was an error connecting. Possibly incorrect syntax?", e);
            }
        }
    }

    /**
     *
     * Close the current database connection.
     *
     */
    public void disconnect() {
        if(connection != null) {
            try {
                connection.close();
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * Creates a new prepared statement.
     *
     * @param query sql query
     * @return new {@link Statement} Instance
     */
    public Statement createStatement(final String query) {
        try {
            return new Statement(this.connection.prepareStatement(query));
        } catch (final Exception e) {
            throw new UnhandledSQLException("There was an error creating the statement", e);
        }
    }

    /**
     *
     * Execute a statement without result.
     *
     * @param query sql query
     * @param arguments arguments for prepared statement
     */
    public void execute(final String query, final Object... arguments) {
        try {
            Statement statement = createStatement(query);
            statement.execute(arguments);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     *
     *  Run something asynchronously.
     *
     * @param callable the Runnable or Callable
     * @param <T> type of return value
     * @return {@link Future<T>}
     */
    public <T> Future<T> runAsync(final Callable<T> callable) {
        return this.executorPool.submit(callable);
    }

}