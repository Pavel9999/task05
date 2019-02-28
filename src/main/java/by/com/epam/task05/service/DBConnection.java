package by.com.epam.task05.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBConnection {
    final static String url = "jdbc:mysql://localhost:3306/rentcar?serverTimezone=Europe/Moscow&useSSL=false";
    final static String username = "root";
    final static String password = "1234";

    public static Connection createConnection()
    {
        Connection con = null;

        try
        {
            try
            {
                Class.forName("com.mysql.jdbc.Driver"); //loading MySQL drivers. This differs for database servers
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            con = DriverManager.getConnection(url, username, password); //attempting to connect to MySQL database
            System.out.println("Printing connection object "+con);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return con;
    }
}
