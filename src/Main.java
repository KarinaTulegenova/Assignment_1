import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryUser user = new LibraryUser("Karina Tulegenova");

        // Adding books to the library
        library.addItem(new Book("1984"));
        library.addItem(new Book("Moby Dick"));
        library.addItem(new Book("The Great Gatsby"));
        library.addItem(new Book("My Little Java"));
        library.addItem(new Book("Journey to the Center of the Earth"));

        System.out.println("Welcome to the library!");
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nFunctions: view, available, sort, borrow, return, exit");
                System.out.print("Enter a command: ");
                String command = scanner.nextLine().trim();

                processCommand(command, library, user, scanner);
            }
        }
    }

    private static void processCommand(String command, Library library, LibraryUser user, Scanner scanner) {
        switch (command.toLowerCase()) {
            case "view":
                library.displayItems();
                break;
            case "available":
                library.displayAvailableItems();
                break;
            case "sort":
                library.displaySortedItems();
                break;
            case "borrow":
                System.out.print("Enter the name of the book: ");
                LibraryItem itemToBorrow = library.findItem(scanner.nextLine());
                if (itemToBorrow != null) user.borrowItem(itemToBorrow);
                break;
            case "return":
                System.out.print("Enter the name of the book: ");
                LibraryItem itemToReturn = library.findItem(scanner.nextLine());
                if (itemToReturn != null) user.returnItem(itemToReturn);
                break;
            case "exit":
                System.out.println("Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid command. Please try again.");
        }
    }
}