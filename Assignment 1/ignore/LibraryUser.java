import java.util.ArrayList;
import java.util.List;

public class LibraryUser {
    private String name;
    private List<LibraryItem> borrowedItems;

    public LibraryUser(String name) {
        this.name = name;
        this.borrowedItems = new ArrayList<>();
    }

    public void borrowItem(LibraryItem item) {
        if (!item.isBorrowed()) {
            item.borrowItem();
            borrowedItems.add(item);
            System.out.println(name + " borrowed: " + item.getTitle());
        } else {
            System.out.println("The item is already borrowed: " + item.getTitle());
        }
    }

    public void returnItem(LibraryItem item) {
        if (borrowedItems.contains(item)) {
            item.returnItem();
            borrowedItems.remove(item);
            System.out.println(name + " returned: " + item.getTitle());
        } else {
            System.out.println(name + " did not borrow this item: " + item.getTitle());
        }
    }
}
