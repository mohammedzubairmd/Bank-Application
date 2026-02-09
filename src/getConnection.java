import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class getConnection {
    // Better practice: Use environment variables or a .properties file
    private static final String URL = "jdbc:mysql://localhost:3306/bank";
    private static final String USER = "root";
    private static final String PASS = "Zubair@2003";

    public static Connection getconnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Current code probably looks like this:
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "Zubair@2003");
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Database Connection Failed: " + e.getMessage(), e);
        }
    }
}