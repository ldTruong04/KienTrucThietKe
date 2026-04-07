class EBookFactory extends BookFactory {
    public Book createBook(String t, String a, String g) {
        return new EBook(t,a,g);
    }
}