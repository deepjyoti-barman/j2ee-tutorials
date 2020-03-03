package com.oracle.jdbc;

/* STEP-1: Import required packages */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

/**
 * It is built to retrieve and display entry / entries of a table in given MySQL database.
 * 
 * @author Deepjyoti Barman
 * @since March 02, 2020
 */
public class DQL_SelectStatement
{
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_NAME     = "testyantra_employee";
    static final String DB_URL      = "jdbc:mysql://localhost:3307/" + DB_NAME;

    // Database credentials
    static final String USER = "root";
    static final String PASS = "password";
    
    // Query to be executed
    static final String MYSQL_QUERY = "SELECT * FROM emp";
    
    public static void main(String[] args)
    {
        Connection con = null;
        Statement statement = null;
        
        try
        {
            /* STEP-2: Register JDBC driver */
            Class.forName(JDBC_DRIVER);

            /* STEP-3: Open a connection */
            System.out.println("Trying to connect to database: " + DB_NAME);
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database `" + DB_NAME + "` successfully!");
            
            /* STEP-4: Create the statement */
            System.out.println("Trying to retrieve existing entries from the given table of `" + DB_NAME + "` database");
            statement = con.createStatement(); 

            /* STEP-5: Execute the query */
            // If DQL failed, it will raise an SQLException
            ResultSet results = statement.executeQuery(MYSQL_QUERY);
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("No of columns present in the table: " + columnCount);
            
            /* STEP-6: Process the result */
            System.out.println("Successfully retrieved all entries from the given table of `" + DB_NAME + "` database");
                
            /*
                Type constants and respective values:
                3  = DECIMAL
                4  = INT
                12 = VARCHAR
                91 = DATE
             */
            System.out.println("Records are: ");
            while (results.next())
            {
                LinkedHashMap<String, Object> records = new LinkedHashMap<>();
                
                for (int i = 1; i <= columnCount; i++)
                {
                    if (metaData.getColumnType(i) == 3)
                        records.put(metaData.getColumnName(i), results.getDouble(i));
                    
                    if (metaData.getColumnType(i) == 4)
                        records.put(metaData.getColumnName(i), results.getInt(i));
                    
                    if (metaData.getColumnType(i) == 12)
                        records.put(metaData.getColumnName(i), results.getString(i));
                    
                    if (metaData.getColumnType(i) == 91)
                        records.put(metaData.getColumnName(i), results.getString(i));
                }

                System.out.println("\t" + records);
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e);
        }
        finally
        {
            /* STEP-7: Close the Connection and the Statement object */
            try
            {
                if (con != null)
                    con.close();
                
                if (statement != null)
                    statement.close();
            }
            catch (SQLException e)
            {
                System.out.println(e);
            }
        }
    }
}