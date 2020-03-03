package com.oracle.jdbc;

/* STEP-1: Import required packages */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * It is built to update existing entry / entries in a table of given MySQL database.
 * 
 * @author Deepjyoti Barman
 * @since March 02, 2020
 */
public class DML_UpdateStatement
{
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_NAME     = "testyantra_employee";
    static final String DB_URL      = "jdbc:mysql://localhost:3307/" + DB_NAME;

    // Database credentials
    static final String USER = "root";
    static final String PASS = "password";
    
    // Query to be executed
    static final String MYSQL_QUERY = "UPDATE emp "
            + "SET"
            + "    salary = 35000.00,"
            + "    joining_date = '2019-07-25'"
            + "WHERE emp_id = 4";
    
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
            System.out.println("Trying to update an existing entry in the given table of `" + DB_NAME + "` database");
            statement = con.createStatement(); 

            /* STEP-5: Execute a query */
            // If DML failed, it will raise an SQLException
            // No of records affected: 0, means no entry is updated (i.e. invalid SQL selector written)
            int rows_affected = statement.executeUpdate(MYSQL_QUERY);
            System.out.println("Successfully updated entry in the given table of `" + DB_NAME + "` database");
            System.out.println("No of records affected: " + rows_affected);
        }
        catch (SQLException | ClassNotFoundException e)
        {
            System.out.println(e);
        }
        finally
        {
            /* STEP-6: Close the Connection and the Statement object */
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