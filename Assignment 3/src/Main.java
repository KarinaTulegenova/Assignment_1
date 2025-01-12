import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System!");
        System.out.print("Please enter your name: ");
        String userName = scanner.nextLine();
        LibraryUser user = new LibraryUser(userName);

        System.out.println("\nHello, " + user.getName() + "! You can manage books, borrow them, and track borrowed books.\n");

        while (true) {
            System.out.println("Commands: add, view, borrow, return, borrowed, sort, exit");
            System.out.print("Enter a command: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("add")) {
                System.out.print("Enter the book title: ");
                String title = scanner.nextLine();
                System.out.print("Enter the book author: ");
                String author = scanner.nextLine();
                System.out.print("Enter the year of publication: ");
                int year = Integer.parseInt(scanner.nextLine());
                library.addBookToDatabase(title, author, year);
            } else if (command.equalsIgnoreCase("view")) {
                library.displayBooksFromDatabase();
            } else if (command.equalsIgnoreCase("borrow")) {
                System.out.print("Enter the book title: ");
                String title = scanner.nextLine();
                Book book = library.findBookInDatabase(title);
                if (book != null && !book.isBorrowed()) {
                    user.borrowBook(book);
                    library.updateBookStatus(title, true);
                } else if (book != null) {
                    System.out.println("The book is already borrowed.");
                } else {
                    System.out.println("Book not found.");
                }
            } else if (command.equalsIgnoreCase("return")) {
                System.out.print("Enter the book title: ");
                String title = scanner.nextLine();
                Book book = library.findBookInDatabase(title);
                if (book != null && book.isBorrowed() && user.getBorrowedBooks().contains(book)) {
                    user.returnBook(book);
                    library.updateBookStatus(title, false);
                } else if (book != null) {
                    System.out.println("This book was not borrowed by " + user.getName() + ".");
                } else {
                    System.out.println("Book not found.");
                }
            } else if (command.equalsIgnoreCase("borrowed")) {
                user.displayBorrowedBooks();
            } else if (command.equalsIgnoreCase("sort")) {
                library.sortBooksById();
            } else if (command.equalsIgnoreCase("exit")) {
                System.out.println("Goodbye, " + user.getName() + "!");
                break;
            } else {
                System.out.println("Unknown command.");
            }
        }

        scanner.close();
    }
}
