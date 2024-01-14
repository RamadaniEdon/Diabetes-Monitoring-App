package city.org.rs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtility {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String DATABASE_USER = "admin";
    private static final String DATABASE_PASSWORD = "mysql";

    // private static final String DATABASE_URL = "jdbc:mysql://r6ze0q02l4me77k3.chr7pe7iynqr.eu-west-1.rds.amazonaws.com/kqkeb9e5irew8xi1";
    // private static final String DATABASE_USER = "vxsnasuvirkou9jo";
    // private static final String DATABASE_PASSWORD = "dbq4s9572rbthsml";
    //This is the Heroku deployment database but we comment it for you to try in your machine if you want
    //There is a sql file in the root of the project that you can import in your local mysql server

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to register JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }
}
