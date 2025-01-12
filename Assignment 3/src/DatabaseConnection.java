import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db"; // Замените на вашу базу данных
    private static final String USER = "postgres"; // Ваш логин PostgreSQL
    private static final String PASSWORD = "P@ssw0rd2025!"; // Пароль PostgreSQL

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

