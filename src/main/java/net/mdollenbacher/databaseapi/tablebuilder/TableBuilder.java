package net.mdollenbacher.databaseapi.tablebuilder;

import net.mdollenbacher.databaseapi.Database;

import java.util.HashMap;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

public class TableBuilder {

    private final Database database;
    private final String tableName;

    private final HashMap<Integer, Column> columns;

    /**
     *
     * Creates a new instance of the table builder.
     *
     * @param database database name
     * @param tableName new table name
     */
    public TableBuilder(final Database database, final String tableName) {
        this.database = database;
        this.tableName = tableName;
        this.columns = new HashMap<>();
    }

    /**
     *
     * Add a new column to the table.
     *
     * @param column new Column
     */
    public TableBuilder addColumn(final Column column) {
        this.columns.put((1+columns.size()), column);
        return this;
    }

    /**
     *
     * Get a column from your table builder.
     *
     * @param index numeric position
     * @return Column from table
     */
    public Column getColumn(final int index) {
        return this.columns.get(index);
    }

    /**
     *
     * Delete one column from your table builder.
     *
     * @param index numeric position
     */
    public TableBuilder removeColumn(final int index) {
        this.columns.remove(index);
        return this;
    }

    /**
     *
     * Create the table and write it into the database.
     *
     */
    public void build() {
        final StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + this.tableName + " (");
        columns.forEach((i, c) -> {
            if(c.hasLength()) query.append("(").append(c.getColumnName()).append(" ").append(c.getColumnType()).append("(").append(c.getLength()).append(")");
            else query.append("(").append(c.getColumnName()).append(" ").append(c.getColumnType());
            if(!(c.isNullable())) query.append(" NOT NULL,");
            else query.append(",");
        });
        query.append(");");

        this.database.execute(query.toString());
    }

    /**
     *
     * Instead of execute the query immediately get the sql query as string
     *
     * @return SQL query as string
     */
    public String getQuery() {
        final StringBuilder query = new StringBuilder("CREATE TABLE IF NOT EXISTS " + this.tableName + " (");
        columns.forEach((i, c) -> {
            if(c.hasLength()) query.append("(").append(c.getColumnName()).append(" ").append(c.getColumnType()).append("(").append(c.getLength()).append(")");
            else query.append("(").append(c.getColumnName()).append(" ").append(c.getColumnType());
            if(!(c.isNullable())) query.append(" NOT NULL,");
            else query.append(",");
        });
        query.append(");");
        return query.toString();
    }

}

