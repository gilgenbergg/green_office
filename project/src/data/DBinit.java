package data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.lang.*;

public class DBinit {
    private static DBinit DBINIT = DBinit.getInstance();
    private final static String url = "jdbc:postgresql://127.0.0.1:5432/green_office";
    private final static String name = "postgres";
    private final static String password = "12345";
    private static Statement statement = null;
    private static Connection connection;

    public DBinit() {
        try
        {
            Connection connection = initConection();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DBinit getInstance() {
        if (DBINIT == null) {
            synchronized (DBinit.class) {
                if (DBINIT == null) {
                    DBINIT = new DBinit();
                }
            }
        }
        return DBINIT;
    }

    public static Connection getConnInst() throws SQLException, ClassNotFoundException {
        try {
            connection = initConection();
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            try {
                connection = initConection();
            }
            catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static Connection initConection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        connection = DriverManager.getConnection(url, name, password);
        statement = connection.createStatement();
        return connection;
    }
}
