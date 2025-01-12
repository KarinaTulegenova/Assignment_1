public abstract class LibraryItem {
    private int id;
    private String title;
    private boolean isBorrowed;

    public LibraryItem(int id, String title, boolean isBorrowed) {
        this.id = id;
        this.title = title;
        this.isBorrowed = isBorrowed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void setBorrowed(boolean borrowed) {
        isBorrowed = borrowed;
    }

    @Override
    public String toString() {
        return id + ": " + title + (isBorrowed ? " (Borrowed)" : " (Available)");
    }
}
