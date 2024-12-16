public class LibraryUser {
    private String name;

    public LibraryUser(String name) {
        this.name = name;
    }

    public void borrowBook(Book book) {
        if (!book.isBorrowed()) {
            book.borrowBook();
            System.out.println(name + " взял книгу: " + book.getTitle());
        } else {
            System.out.println("Книга уже занята.");
        }
    }

    public void returnBook(Book book) {
        if (book.isBorrowed()) {
            book.returnBook();
            System.out.println(name + " вернул книгу: " + book.getTitle());
        } else {
            System.out.println("Книга уже доступна.");
        }
    }
}
