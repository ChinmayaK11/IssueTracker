import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL      = System.getenv("DB_URL")      != null ? System.getenv("DB_URL")      : "jdbc:mysql://localhost:3306/issue_tracker_db";
    private static final String USER     = System.getenv("DB_USER")     != null ? System.getenv("DB_USER")     : "root";
    private static final String PASSWORD = System.getenv("DB_PASSWORD") != null ? System.getenv("DB_PASSWORD") : "";

    // Private constructor — prevent instantiation
    private DBConnection() {}

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            System.err.println("❌ MySQL Driver not found! Add mysql-connector-j to classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("❌ Database connection failed! Check your DB credentials.");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Database connected successfully!");
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.err.println("❌ Could not establish database connection.");
        return false;
    }
}
