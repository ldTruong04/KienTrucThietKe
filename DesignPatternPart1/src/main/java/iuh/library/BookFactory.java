abstract class BookFactory {
    public abstract Book createBook(String t, String a, String g);
}

class PaperBookFactory extends BookFactory {
    public Book createBook(String t, String a, String g) {
        return new PaperBook(t,a,g);
    }
}
