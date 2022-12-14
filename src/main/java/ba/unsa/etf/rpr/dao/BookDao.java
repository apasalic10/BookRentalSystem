package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;

import java.util.List;

public interface BookDao extends Dao<Book> {
    /**
     * return list of the books by the given author
     * @param author - the author of the books
     * @return list of books by the given author
     */
    public List<Book> searchByAuthor(String author);

    /**
     * return list of the books which have the given text in their name
     * @param text - the text by which books are searched
     * @return list of the books  which have the given text in their name
     */
    public List<Book> searchByText(String text);

    /**
     * return list of books for the given library
     * @param library - the library where books are searched
     * @return list of books for the given library
     */
    List<Book> searchByLibrary(Library library);

    /**
     * return list of all available books
     * @return list of available books
     */
    List<Book> searchAllAvailable();
}
