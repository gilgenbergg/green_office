package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.*;

public class DBinit {
    public static Connection connection = null;
    public final static String url = "jdbc:postgresql://127.0.0.1:5432/green_office";
    public final static String name = "postgres";
    public final static String password = "12345";
    public static Statement statement = null;

    public static void initConection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        System.out.println("Driver is on...");
        connection = DriverManager.getConnection(url, name, password);
        System.out.println("Connection has been established");
        statement = connection.createStatement();
    }
}
