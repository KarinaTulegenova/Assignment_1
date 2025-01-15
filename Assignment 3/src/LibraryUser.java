import java.util.ArrayList;
import java.util.List;

public class LibraryUser {
    private int id;
    private String name;
    private String email;
    private List<String> borrowedBooks;

    public LibraryUser(int id, String name, String email, List<String> borrowedBooks) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.borrowedBooks = borrowedBooks;
    }

    public String getName() {
        return name;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book.getTitle());
        System.out.println(name + " borrowed the book: " + book.getTitle());
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book.getTitle());
        System.out.println(name + " returned the book: " + book.getTitle());
    }

    public void displayBorrowedBooks() {
        System.out.println(name + " has borrowed the following books:");
        for (String title : borrowedBooks) {
            System.out.println("- " + title);
        }
    }
}
