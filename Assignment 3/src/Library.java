import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {

    public void addBookToDatabase(String title, String author, int year) {
        String sql = "INSERT INTO books (title, author, year_of_publication, is_borrowed) VALUES (?, ?, ?, false)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
            System.out.println("Book added: " + title);
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void displayBooksFromDatabase() {
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year_of_publication");
                boolean isBorrowed = rs.getBoolean("is_borrowed");
                System.out.println(new Book(id, title, author, year, isBorrowed));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
    }

    public Book findBookInDatabase(String title) {
        String sql = "SELECT * FROM books WHERE title = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year_of_publication"),
                        rs.getBoolean("is_borrowed")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error finding book: " + e.getMessage());
        }
        return null;
    }

    public void updateBookStatus(String title, boolean isBorrowed) {
        String sql = "UPDATE books SET is_borrowed = ? WHERE title = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, isBorrowed);
            pstmt.setString(2, title);
            pstmt.executeUpdate();
            System.out.println("Book status updated for: " + title);
        } catch (SQLException e) {
            System.out.println("Error updating book status: " + e.getMessage());
        }
    }

    public void sortBooksById() {
        String sql = "SELECT * FROM books ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year_of_publication");
                boolean isBorrowed = rs.getBoolean("is_borrowed");
                System.out.println(new Book(id, title, author, year, isBorrowed));
            }
        } catch (SQLException e) {
            System.out.println("Error sorting books: " + e.getMessage());
        }
    }
    public void deleteBookFromDatabase(String title) {
        String sql = "DELETE FROM books WHERE title = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Book deleted: " + title);
            } else {
                System.out.println("No book found with title: " + title);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }

}
