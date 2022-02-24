package data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Fabian-Bob Ioan George
 * here is my class for the connection to the database
 */

public class DatabaseConnection {
    public Connection databaseLink;

    /**
     * @return databaseLink is the link which makes the connection between
     * my application and the actual database made in MySQL Workbench
     */

    public Connection getConnection(){
        String databaseName = "OrderManagement";
        String databaseUser = "root";
        String databasePassword = "123456789";
        String url = "jdbc:mysql://localhost:3306/" + databaseName + "?serverTimezone=UTC";

        try{
            /**
             * here i merge all the strings and create the final database string
             */
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
        return databaseLink;
    }
}
