import java.util.ArrayList;
import java.util.List;

public class LibraryUser {
    private String name;
    private List<Book> borrowedBooks = new ArrayList<>();

    public LibraryUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            book.setBorrowed(true);
            borrowedBooks.add(book);
            System.out.println(name + " взял книгу: " + book.getTitle());
        } else {
            System.out.println("Книга " + book.getTitle() + " уже занята.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.setBorrowed(false);
            borrowedBooks.remove(book);
            System.out.println(name + " вернул книгу: " + book.getTitle());
        } else {
            System.out.println("Эта книга не была взята " + name + ".");
        }
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println(name + " не взял ни одной книги.");
        } else {
            System.out.println(name + " взял следующие книги:");
            for (Book book : borrowedBooks) {
                System.out.println("- " + book.getTitle());
            }
        }
    }
}
