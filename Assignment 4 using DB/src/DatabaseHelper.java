import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class DatabaseHelper {
    private static final String URL = "jdbc:postgresql://localhost:5432/library_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "P@ssw0rd2025!";


    public static JSONObject getBookData(String title) {
        JSONObject bookData = new JSONObject();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT * FROM books_2 WHERE title LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, "%" + title + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                bookData.put("id", rs.getInt("id"));
                bookData.put("title", rs.getString("title"));
                bookData.put("author", rs.getString("author"));
                bookData.put("genre", rs.getString("genre"));
                bookData.put("year", rs.getString("published_year"));
                bookData.put("pages", rs.getInt("pages"));
                bookData.put("cover", rs.getString("cover_url"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookData;
    }

    public static void addBook(String title, String author, String genre, String year, int pages, String coverUrl) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO books_2 (title, author, genre, published_year, pages, cover_url) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, genre);
            stmt.setString(4, year);
            stmt.setInt(5, pages);
            stmt.setString(6, coverUrl);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteBook(String title) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "DELETE FROM books_2 WHERE title = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, title);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void registerUser(String username, String password) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void borrowBook(int userId, int bookId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO borrowed_books (user_id, book_id, borrow_date) VALUES (?, ?, NOW())";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean returnBook(String username, int bookId) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "DELETE FROM borrowed_books WHERE user_id = (SELECT id FROM users WHERE username = ?) AND book_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setInt(2, bookId);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0; // Если запись была удалена, вернёт true
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<String> getUserBorrowedBooks(String username) {
        List<String> books = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT b.title FROM borrowed_books bb JOIN books_2 b ON bb.book_id = b.id WHERE bb.user_id = (SELECT id FROM users WHERE username = ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                books.add(rs.getString("title"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }


}
