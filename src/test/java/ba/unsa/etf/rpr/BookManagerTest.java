package ba.unsa.etf.rpr;

import ba.unsa.etf.rpr.business.BookManager;
import ba.unsa.etf.rpr.business.LibraryManager;
import ba.unsa.etf.rpr.domain.Book;
import ba.unsa.etf.rpr.exceptions.BookException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BookManagerTest {
    BookManager bookManager = new BookManager();
    LibraryManager libraryManager = new LibraryManager();
    /**
     * Tests whether the method sets the availability of the book to false
     */
    @Test
    void setAvailableOnFalseTest() throws BookException {

        bookManager.add(createBook());

        bookManager.setAvailableOnFalse(bookManager.getByName("DDD").getId());

        Assertions.assertEquals(0,bookManager.getByName("DDD").getIsAvailable());

        deleteBook();
    }

    /**
     * Test getByName method from BookManager class
     * @throws BookException
     */
    @Test
    void getByNameTest() throws BookException{
        bookManager.add(createBook());

        Assertions.assertEquals("Almedin", bookManager.getByName("DDD").getBookAuthor());

        deleteBook();
    }

    /**
     * Test setAvailableOnTrue method from BookManager class
     * @throws BookException
     */
    @Test
    void setAvailableOnTrueTest() throws BookException {
        bookManager.add(createBookTemp());

        bookManager.setAvailableOnTrue(bookManager.getByName("DDD").getId());

        Assertions.assertEquals(0,bookManager.getByName("DDD").getIsAvailable());

        deleteBookTemp();

    }

    /**
     * Create book for tests
     * @throws BookException
     */
    private Book createBook() throws BookException {
        Book b = new Book();
        b.setBookName("DDD");
        b.setBookAuthor("Almedin");
        b.setIsAvailable(1);
        b.setBookLibrary(libraryManager.getByName("BuyBook"));

        return b;
    }

    /**
     * Create book for tests
     * @throws BookException
     */
    private Book createBookTemp() throws BookException {
        Book b = new Book();
        b.setBookName("AAA");
        b.setBookAuthor("Niko");
        b.setIsAvailable(0);
        b.setBookLibrary(libraryManager.getByName("BuyBook"));

        return b;
    }


    /**
     * Delete object from database after test
     * @throws BookException
     */
    private void deleteBook() throws BookException {
        bookManager.delete(bookManager.getByName("DDD").getId());
    }

    /**
     * Delete object from database after test
     * @throws BookException
     */
    private void deleteBookTemp() throws BookException {
        bookManager.delete(bookManager.getByName("AAA").getId());
    }




}
