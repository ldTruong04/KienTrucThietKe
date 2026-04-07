public class MainApp {
    public static void main(String[] args) {
        Library lib = Library.getInstance();

        // Observer
        User u1 = new User("Truong");
        lib.addObserver(u1);

        // Factory
        BookFactory factory = new PaperBookFactory();
        Book b1 = factory.createBook("Java Core", "James", "IT");
        lib.addBook(b1);

        factory = new EBookFactory();
        Book b2 = factory.createBook("Spring Boot", "Rod", "IT");
        lib.addBook(b2);

        // Strategy
        SearchContext context = new SearchContext();
        context.setStrategy(new SearchByTitle());

        System.out.println("\nTìm theo tên:");
        for(Book b : context.execute(lib.getBooks(), "Java"))
            System.out.println(b);

        // Decorator
        Borrow borrow = new BasicBorrow();
        borrow = new ExtendBorrow(borrow);
        borrow = new SpecialEdition(borrow);
System.out.println("\nBorrow: " + borrow.getDescription());
    }
}