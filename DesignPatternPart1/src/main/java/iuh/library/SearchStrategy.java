import java.util.ArrayList;
import java.util.List;

interface SearchStrategy {
    List<Book> search(List<Book> books, String keyword);
}

class SearchByTitle implements SearchStrategy {
    public List<Book> search(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for(Book b: books)
            if(b.getTitle().toLowerCase().contains(keyword.toLowerCase()))
                result.add(b);
        return result;
    }
}

class SearchByAuthor implements SearchStrategy {
    public List<Book> search(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for(Book b: books)
            if(b.getAuthor().toLowerCase().contains(keyword.toLowerCase()))
                result.add(b);
        return result;
    }
}
class SearchByGenre implements SearchStrategy {
    public List<Book> search(List<Book> books, String keyword) {
        List<Book> result = new ArrayList<>();
        for(Book b: books)
            if(b.getGenre().toLowerCase().contains(keyword.toLowerCase()))
                result.add(b);
        return result;
    }
}

class SearchContext {
    private SearchStrategy strategy;

    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<Book> execute(List<Book> books, String keyword) {
        return strategy.search(books, keyword);
    }
}
