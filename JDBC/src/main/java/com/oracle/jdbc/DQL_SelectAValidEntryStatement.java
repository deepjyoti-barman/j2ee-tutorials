package com.oracle.jdbc;

/* STEP-1: Import required packages */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * It is built to retrieve and display a particular entry of a table in given MySQL database.
 * 
 * @author Deepjyoti Barman
 * @since March 02, 2020
 */
public class DQL_SelectAValidEntryStatement
{
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_NAME     = "testyantra_employee";
    static final String DB_URL      = "jdbc:mysql://localhost:3307/" + DB_NAME;

    // Database credentials
    static final String USER = "root";
    static final String PASS = "password";
    
    // Query to be executed
    static final String MYSQL_QUERY = "SELECT * FROM emp WHERE emp_id = 2";
    
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
            System.out.println("Trying to retrieve an entry from the given table of `" + DB_NAME + "` database");
            statement = con.createStatement(); 

            /* STEP-5: Execute the query */
            // If DQL failed, it will raise an SQLException
            ResultSet results = statement.executeQuery(MYSQL_QUERY);
            ResultSetMetaData metaData = results.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("No of columns present in the table: " + columnCount);
            
            /* STEP-6: Process the result */
            System.out.println("Retrieved the entry from the given table of `" + DB_NAME + "` database");
            System.out.println("Record is: ");
            if (results.next())
            {
                System.out.println("\t" + "Employee ID: " + results.getInt("emp_id"));
                System.out.println("\t" + "First name: " + results.getString("first_name"));
                System.out.println("\t" + "Last name: " + results.getString("last_name"));
                System.out.println("\t" + "Salary: " + results.getDouble("salary"));
                System.out.println("\t" + "Date of joining: " + results.getString("joining_date"));
            }
            else
                System.out.println("\tSORRY! Entry that you are trying to access does not exist in given the table of `" + DB_NAME + "` database");
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