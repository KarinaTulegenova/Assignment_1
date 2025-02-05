import org.json.simple.JSONObject;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;


public class BookAppGui extends JFrame {
    private JSONObject bookData;
    private JLabel bookCover, titleText, authorText, genreText, yearText, pagesText, idText;
    private JTextField searchTextField;
    private String currentUser = null;

    public BookAppGui() {
        super("Digital Library System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        addGuiComponents();
    }

    private void addGuiComponents() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchTextField = new JTextField(25);
        JButton searchButton = new JButton("🔍");
        JButton registerButton = new JButton("Register");
        topPanel.add(searchTextField);
        topPanel.add(searchButton);
        topPanel.add(registerButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        bookCover = new JLabel();
        centerPanel.add(bookCover);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        idText = new JLabel("ID: ");
        titleText = new JLabel("Title: ");
        authorText = new JLabel("Author: ");
        genreText = new JLabel("Genre: ");
        yearText = new JLabel("Published Year: ");
        pagesText = new JLabel("Pages: ");

        idText.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleText.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorText.setAlignmentX(Component.CENTER_ALIGNMENT);
        genreText.setAlignmentX(Component.CENTER_ALIGNMENT);
        yearText.setAlignmentX(Component.CENTER_ALIGNMENT);
        pagesText.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottomPanel.add(idText);
        bottomPanel.add(titleText);
        bottomPanel.add(authorText);
        bottomPanel.add(genreText);
        bottomPanel.add(yearText);
        bottomPanel.add(pagesText);
        add(bottomPanel, BorderLayout.SOUTH);

        JButton addBookButton = new JButton("Add Book");
        JButton deleteBookButton = new JButton("Delete Book");
        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton myBooksButton = new JButton("My Borrowed Books");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(borrowBookButton);
        buttonPanel.add(returnBookButton);
        buttonPanel.add(myBooksButton);
        bottomPanel.add(buttonPanel);

        searchButton.addActionListener(e -> searchBook());
        addBookButton.addActionListener(e -> openAddBookDialog());
        deleteBookButton.addActionListener(e -> deleteBook());
        borrowBookButton.addActionListener(e -> openBorrowDialog());
        returnBookButton.addActionListener(e -> returnBook());
        registerButton.addActionListener(e -> openRegisterDialog());
        myBooksButton.addActionListener(e -> showBorrowedBooks());
    }


    private void searchBook() {
        String userInput = searchTextField.getText().trim();
        if (userInput.isEmpty()) return;
        bookData = DatabaseHelper.getBookData(userInput);
        if (bookData == null || bookData.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Book not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        idText.setText("ID: " + bookData.get("id"));
        titleText.setText("Title: " + bookData.get("title"));
        authorText.setText("Author: " + bookData.get("author"));
        genreText.setText("Genre: " + bookData.get("genre"));
        yearText.setText("Published Year: " + bookData.get("year"));
        pagesText.setText("Pages: " + bookData.get("pages"));
        bookCover.setIcon(loadImage((String) bookData.get("cover")));
    }



    private void openAddBookDialog() {
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField genreField = new JTextField();
        JTextField yearField = new JTextField();
        JTextField pagesField = new JTextField();
        JTextField coverField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(6, 2));
        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("Genre:"));
        panel.add(genreField);
        panel.add(new JLabel("Published Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Pages:"));
        panel.add(pagesField);
        panel.add(new JLabel("Cover URL:"));
        panel.add(coverField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            DatabaseHelper.addBook(
                    titleField.getText(),
                    authorField.getText(),
                    genreField.getText(),
                    yearField.getText(),
                    Integer.parseInt(pagesField.getText()),
                    coverField.getText()
            );
            JOptionPane.showMessageDialog(null, "Book added successfully!");
        }
    }

    private void deleteBook() {
        String bookTitle = JOptionPane.showInputDialog("Enter book title to delete:");
        if (bookTitle != null && !bookTitle.trim().isEmpty()) {
            DatabaseHelper.deleteBook(bookTitle);
            JOptionPane.showMessageDialog(null, "Book deleted (if it existed).");
        }
    }


    private void openBorrowDialog() {
        JTextField userIdField = new JTextField();
        JTextField bookIdField = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);
        panel.add(new JLabel("Book ID:"));
        panel.add(bookIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Borrow Book", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                int bookId = Integer.parseInt(bookIdField.getText());
                DatabaseHelper.borrowBook(userId, bookId);
                JOptionPane.showMessageDialog(null, "Book borrowed successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid ID format!");
            }
        }
    }

    private void openRegisterDialog() {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Register", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (!username.isEmpty() && !password.isEmpty()) {
                DatabaseHelper.registerUser(username, password);
                JOptionPane.showMessageDialog(null, "User registered successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter valid credentials!");
            }
        }
    }
    private void returnBook() {
        String bookIdInput = JOptionPane.showInputDialog("Enter Book ID to return:");
        if (bookIdInput == null || bookIdInput.trim().isEmpty()) return;

        try {
            int bookId = Integer.parseInt(bookIdInput);
            if (DatabaseHelper.returnBook(currentUser, bookId)) {
                JOptionPane.showMessageDialog(this, "Book returned successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "You have not borrowed this book!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid Book ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void showBorrowedBooks() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please log in first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder borrowedBooks = new StringBuilder();
        List<String> books = DatabaseHelper.getUserBorrowedBooks(currentUser);

        if (books.isEmpty()) {
            borrowedBooks.append("No books borrowed.");
        } else {
            for (String book : books) {
                borrowedBooks.append(book).append("\n");
            }
        }

        JOptionPane.showMessageDialog(this, borrowedBooks.toString(), "My Borrowed Books", JOptionPane.INFORMATION_MESSAGE);
    }

    private ImageIcon loadImage(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            BufferedImage originalImage = ImageIO.read(url);
            int targetWidth = 300;
            int targetHeight = 400;
            Image scaledImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookAppGui().setVisible(true));
    }
}
