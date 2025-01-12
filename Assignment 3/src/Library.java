import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Library {

    public void addBookToDatabase(String title, String author, int year) {
        String sql = "INSERT INTO books (title, author, year_of_publication, is_borrowed) VALUES (?, ?, ?, FALSE)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, author);
            pstmt.setInt(3, year);
            pstmt.executeUpdate();
            System.out.println("Book added to the database: " + title);
        } catch (SQLException e) {
            System.out.println("Error while adding the book: " + e.getMessage());
        }
    }

    public void displayBooksFromDatabase() {
        String sql = "SELECT * FROM books";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("List of books:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year_of_publication");
                boolean isBorrowed = rs.getBoolean("is_borrowed");
                System.out.println(id + ": " + title + " by " + author + " (" + year + ")" +
                        (isBorrowed ? " (Borrowed)" : " (Available)"));
            }
        } catch (SQLException e) {
            System.out.println("Error while displaying books: " + e.getMessage());
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
            System.out.println("Error while finding the book: " + e.getMessage());
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
            System.out.println("Error while updating book status: " + e.getMessage());
        }
    }

    // New method to sort books by ID
    public void sortBooksById() {
        String sql = "SELECT * FROM books ORDER BY id ASC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("Books sorted by ID:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                int year = rs.getInt("year_of_publication");
                boolean isBorrowed = rs.getBoolean("is_borrowed");
                System.out.println(id + ": " + title + " by " + author + " (" + year + ")" +
                        (isBorrowed ? " (Borrowed)" : " (Available)"));
            }
        } catch (SQLException e) {
            System.out.println("Error while sorting books: " + e.getMessage());
        }
    }
}
