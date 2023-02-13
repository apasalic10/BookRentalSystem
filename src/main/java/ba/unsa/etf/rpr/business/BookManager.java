package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.Dao;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.domain.Library;
import ba.unsa.etf.rpr.domain.Member;
import ba.unsa.etf.rpr.exceptions.BookException;

import java.util.List;

/**
 * Business Logic Layer for Book
 *
 * @author Pasalic Almedin
 */
public class BookManager {
    public List<Book> getAll() throws BookException {
        return DaoFactory.bookDao().getAll();
    }

    public void delete(int id) throws BookException{
        DaoFactory.bookDao().delete(id);
    }

    public Book getById(int quoteId) throws BookException{
        return DaoFactory.bookDao().getById(quoteId);
    }

    public void update(Book q) throws BookException{
        DaoFactory.bookDao().update(q);
    }

    public Book add(Book q) throws BookException{
        return DaoFactory.bookDao().add(q);
    }


    public List<Book> searchByAuthor(String author) throws BookException{
        return DaoFactory.bookDao().searchByAuthor(author);
    }


    public List<Book> searchByText(String text) throws BookException{
        return DaoFactory.bookDao().searchByText(text);
    }


   public List<Book> searchByLibrary(Library library) throws BookException{
        return DaoFactory.bookDao().searchByLibrary(library);
    }

    public List<Book> searchAllAvailable() throws BookException{
        return DaoFactory.bookDao().searchAllAvailable();
    }
}
