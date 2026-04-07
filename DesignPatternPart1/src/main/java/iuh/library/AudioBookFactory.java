class AudioBookFactory extends BookFactory {
    public Book createBook(String t, String a, String g) {
        return new AudioBook(t,a,g);
    }
}