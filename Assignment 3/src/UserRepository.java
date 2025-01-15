import java.sql.*;
import java.util.List;

public class UserRepository {

    // Добавление нового пользователя
    public void addUser(String name, String email) {
        String sql = "INSERT INTO library_users (name, email, borrowed_books) VALUES (?, ?, ?::jsonb)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, "[]"); // Пустой массив JSON
            pstmt.executeUpdate();
            System.out.println("User added: " + name);
        } catch (SQLException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // Обновление списка заимствованных книг
    // Обновление списка заимствованных книг для пользователя
    public void updateBorrowedBooks(String name, List<String> borrowedBooks) {
        String sql = "UPDATE library_users SET borrowed_books = ?::jsonb WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Преобразование списка в строку JSON
            String borrowedBooksJson = borrowedBooks.stream()
                    .map(book -> "\"" + book.replace("\"", "\\\"") + "\"")
                    .reduce((a, b) -> a + ", " + b)
                    .map(books -> "[" + books + "]")
                    .orElse("[]");

            pstmt.setString(1, borrowedBooksJson);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            System.out.println("Borrowed books updated for user: " + name);
        } catch (SQLException e) {
            System.out.println("Error updating borrowed books: " + e.getMessage());
            e.printStackTrace();
        }
    }


    // Вывод всех пользователей
    public void viewUsers() {
        String sql = "SELECT * FROM library_users";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("All users in the library:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String borrowedBooksJson = rs.getString("borrowed_books");

                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Borrowed Books: " + borrowedBooksJson);
            }
        } catch (SQLException e) {
            System.out.println("Error viewing users: " + e.getMessage());
        }
    }
}
