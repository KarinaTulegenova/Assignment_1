import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        LibraryUser user = new LibraryUser("Alice");

        // Добавляем книги в библиотеку
        library.addBook(new Book("1984"));
        library.addBook(new Book("Moby Dick"));

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nКоманды: view, borrow, return, exit");
            System.out.print("Введите команду: ");
            command = scanner.nextLine();

            if (command.equalsIgnoreCase("view")) {
                library.displayBooks();
            } else if (command.equalsIgnoreCase("borrow")) {
                System.out.print("Введите название книги: ");
                Book book = library.findBook(scanner.nextLine());
                if (book != null) user.borrowBook(book);
                else System.out.println("Книга не найдена.");
            } else if (command.equalsIgnoreCase("return")) {
                System.out.print("Введите название книги: ");
                Book book = library.findBook(scanner.nextLine());
                if (book != null) user.returnBook(book);
                else System.out.println("Книга не найдена.");
            } else if (command.equalsIgnoreCase("exit")) {
                System.out.println("До свидания!");
                break;
            } else {
                System.out.println("Неизвестная команда.");
            }
        }

        scanner.close();
    }
}
