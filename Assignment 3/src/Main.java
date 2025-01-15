import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        UserRepository userRepository = new UserRepository();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Library Management System!");

        System.out.print("Please enter your name: ");
        String userName = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        // Создание нового пользователя
        LibraryUser user = new LibraryUser(-1, userName, email, new ArrayList<>());
        userRepository.addUser(userName, email);

        System.out.println("\nHello, " + user.getName() + "! You can manage books, borrow them, and track borrowed books.\n");

        while (true) {
            System.out.println("\nCommands: add, view, borrow, return, borrowed, sort, delete, viewusers, adduser, exit");
            System.out.print("Enter a command: ");
            String command = scanner.nextLine().trim();

            try {
                switch (command.toLowerCase()) {
                    case "add":
                        System.out.print("Enter the book title: ");
                        String title = scanner.nextLine();
                        System.out.print("Enter the book author: ");
                        String author = scanner.nextLine();
                        System.out.print("Enter the year of publication: ");
                        int year = Integer.parseInt(scanner.nextLine());
                        library.addBookToDatabase(title, author, year);
                        break;
                    case "view":
                        library.displayBooksFromDatabase();
                        break;
                    case "borrow":
                        System.out.print("Enter the book title: ");
                        String borrowTitle = scanner.nextLine();
                        Book book = library.findBookInDatabase(borrowTitle);
                        if (book != null && !book.isBorrowed()) {
                            user.borrowBook(book);
                            userRepository.updateBorrowedBooks(user.getName(), user.getBorrowedBooks());
                            library.updateBookStatus(borrowTitle, true);
                        } else if (book != null) {
                            System.out.println("The book is already borrowed.");
                        } else {
                            System.out.println("Book not found.");
                        }
                        break;
                    case "return":
                        System.out.print("Enter the book title: ");
                        String returnTitle = scanner.nextLine();
                        Book returnBook = library.findBookInDatabase(returnTitle);
                        if (returnBook != null && returnBook.isBorrowed() && user.getBorrowedBooks().contains(returnBook.getTitle())) {
                            user.returnBook(returnBook);
                            userRepository.updateBorrowedBooks(user.getName(), user.getBorrowedBooks());
                            library.updateBookStatus(returnTitle, false);
                        } else if (returnBook != null) {
                            System.out.println("This book was not borrowed by " + user.getName() + ".");
                        } else {
                            System.out.println("Book not found.");
                        }
                        break;
                    case "borrowed":
                        user.displayBorrowedBooks();
                        break;
                    case "sort":
                        library.sortBooksById();
                        break;
                    case "delete":
                        System.out.print("Enter the book title to delete: ");
                        String deleteTitle = scanner.nextLine();
                        library.deleteBookFromDatabase(deleteTitle);
                        break;
                    case "viewusers":
                        System.out.println("List of users:");
                        userRepository.viewUsers();
                        break;
                    case "adduser":
                        System.out.print("Enter the name of the new user: ");
                        String newUserName = scanner.nextLine();
                        System.out.print("Enter the email of the new user: ");
                        String newUserEmail = scanner.nextLine();
                        userRepository.addUser(newUserName, newUserEmail);
                        System.out.println("User added: " + newUserName);
                        break;
                    case "exit":
                        System.out.println("Goodbye, " + user.getName() + "!");
                        return;
                    default:
                        System.out.println("Unknown command. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

    }
}
