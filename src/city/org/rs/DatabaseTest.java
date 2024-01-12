package city.org.rs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseTest {

    public static void main(String[] args) {
        // Test database connection
        try (Connection connection = ConnectionUtility.getConnection()) {
            System.out.println("Connection successful!");

            // Example query
            String query = "SELECT 1"; // Or any simple query appropriate for your database
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {

                if (rs.next()) {
                    System.out.println("Query executed successfully: " + rs.getInt(1));
                }
            } catch (SQLException e) {
                System.err.println("Query failed: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Failed to make database connection: " + e.getMessage());
        }
    }
}
