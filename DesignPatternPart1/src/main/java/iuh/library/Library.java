import java.util.ArrayList;
import java.util.List;

class Library {
    private static Library instance;
    private List<Book> books = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();

    private Library() {}

    public static Library getInstance() {
        if(instance == null) instance = new Library();
        return instance;
    }

    public void addBook(Book b) {
        books.add(b);
        notifyObservers("Thêm sách: " + b.getTitle());
    }

    public void removeBook(Book b) {
        books.remove(b);
    }

    public List<Book> getBooks() { return books; }

    public void addObserver(Observer o) {
        observers.add(o);
    }
    public void notifyObservers(String msg) {
        for(Observer o : observers) {
            o.update(msg);
        }
    }
}