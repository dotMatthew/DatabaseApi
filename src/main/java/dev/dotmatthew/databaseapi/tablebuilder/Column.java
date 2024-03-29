package dev.dotmatthew.databaseapi.tablebuilder;

import lombok.Data;

/**
 * @author dotMatthew
 * @copyright by dotMatthew
 **/

@Data
public class Column {

    private final String columnName;
    private ColumnTypes columnType;
    private final int length;
    private final boolean isNullable;

    public Column(final String columnName, final ColumnTypes columnTypes) {
        this.columnName = columnName;
        this.columnType = columnTypes;
        if(columnTypes.equals(ColumnTypes.VARCHAR)) this.length = 65534;
        else this.length = -10;
        this.isNullable = true;
    }

    public Column(final String columnName, final ColumnTypes columnTypes, final int length) {
        this.columnName = columnName;
        this.columnType = columnTypes;
        this.length = length;
        this.isNullable = true;
    }

    public Column(final String columnName, final ColumnTypes columnTypes, final boolean isNullable) {
        this.columnName = columnName;
        this.columnType = columnTypes;
        this.length = -10;
        this.isNullable = isNullable;
    }

    public Column(final String columnName, final ColumnTypes columnTypes, final int length, final boolean isNullable) {
        this.columnName = columnName;
        this.columnType = columnTypes;
        this.length = length;
        this.isNullable = isNullable;
    }

    public boolean hasLength() {
        return (this.length != -10);
    }

}

