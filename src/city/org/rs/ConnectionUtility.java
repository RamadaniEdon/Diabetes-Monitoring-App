package city.org.rs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    // Database credentials and URL
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "mysql";

    // Static block to register JDBC driver
    static {
        try {
            // This can vary depending on your database (e.g., org.postgresql.Driver for PostgreSQL)
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register JDBC driver", e);
        }
    }

    // Method to get a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
