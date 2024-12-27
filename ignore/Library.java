import java.util.*;
import java.util.stream.Collectors;

public class Library {
    private List<LibraryItem> items = new ArrayList<>();

    public void addItem(LibraryItem item) {
        items.add(item);
    }

    public void displayItems() {
        System.out.println("\n===== All Books =====");
        for (LibraryItem item : items) {
            System.out.println(item);
        }
        System.out.println("=====================");
    }

    public LibraryItem findItem(String title) {
        for (LibraryItem item : items) {
            if (item.getTitle().equalsIgnoreCase(title)) {
                return item;
            }
        }
        System.out.println("Book not found: " + title);
        return null;
    }

    public void displayAvailableItems() {
        List<LibraryItem> availableItems = items.stream()
                .filter(item -> !item.isBorrowed())
                .collect(Collectors.toList());
        System.out.println("\n===== Available Books =====");
        for (LibraryItem item : availableItems) {
            System.out.println(item);
        }
        System.out.println("===========================");
    }

    public void sortItems() {
        Collections.sort(items, Comparator.comparing(LibraryItem::getTitle));
    }

    public void displaySortedItems() {
        sortItems();
        System.out.println("\n===== Sorted Books =====");
        displayItems();
    }
}