# DatabaseApi

This database api is written to help at working with sql in java.

### Features
- create a sql table without knowledge of sql
- create Statements without Try-Catch
- get the result of a Future without Try-Catch

### How to use the api
```java
            // create the database object to connect to the database
            final Database database = new Database(DatabaseType.MySQL,
                    "localhost",
                    3306,
                    "testdb",
                    "testuser",
                    "testpasswd");
    
    
            /*
            Create a table with TableBuilder.java
             */
            TableBuilder builder = new TableBuilder(database, "NameOfTheTable");
            String query = builder.addColumn(new Column(
                    "NameOfTheColumn", ColumnTypes.VARCHAR, 200, false)).getQuery(); // get the query as String
    
            builder.addColumn(new Column("TestColumn", ColumnTypes.BOOLEAN, false)); // add a new Column to the TableBuilder
    
            // execute the query
            builder.build();
    
            final Statement statement = database.createStatement("SELECT 1+1 FROM DUAL ## ORACLE LIKE ;D");
            /**
             * if you need arguments than put them in you query:
             * SELECT name FROM dual WHERE uid = ?;
             */
    
            // Executes a statement without any result
            statement.execute(
                    "arguments", "next argument is an int" , 10, true); // arguments can be any type of object
    
            statement.executeQuery("arguments").get("nameOfTheColumn", String.class); // return the result as String
            statement.executeQuery("arguments").getList("nameOfColumn", String.class); // return a List of Strings
    
            // If you need to get the result of a future you can use {@link StatementResult}
            Future<String> emptyFuture = null;
    
            String futureResult = new StatementResult(emptyFuture).get(String.class); // get the result from the future as String
```