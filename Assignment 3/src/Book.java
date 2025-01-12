import java.util.Objects;

public class Book {
    private int id;
    private String title;
    private String author;
    private int yearOfPublication;
    private boolean isBorrowed;

    public Book(int id, String title, String author, int yearOfPublication, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.isBorrowed = isBorrowed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYearOfPublication() {
        return yearOfPublication;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return title.equals(book.title); // Сравнение по названию
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return title + " by " + author + " (" + yearOfPublication + ")" +
                (isBorrowed ? " (Borrowed)" : " (Available)");
    }
}
