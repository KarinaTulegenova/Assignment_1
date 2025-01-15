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
    public String toString() {
        return id + ". " + title + " by " + author + " (" + yearOfPublication + ") - " +
                (isBorrowed ? "Borrowed" : "Available");
    }
}
